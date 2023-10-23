/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import core.login.UserModel;
import core.login.UsersTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Artyom
 */
@WebListener
public class DBConn {
    public boolean connected;
    private final static String CONN_LOGIN = "iequa";
    private final static String CONN_PASS = "UserPassword1";
    private final static String CONN_URL = "jdbc:sqlanywhere:kursbd";
    
    public Statement stat;
    public DatabaseMetaData meta;
    public Connection con;
    public ResultSet res;
    private Driver mydriv;
    private final static String LOG_SQL = "SELECT * FROM USERS";

    
    
public boolean tryConn(){
    if (connected) {
        return true;
    }
    mydriv = new sap.jdbc4.sqlanywhere.IDriver();
    try {
            //DriverManager.registerDriver(mydriv);
            con = DriverManager.getConnection(CONN_URL,CONN_LOGIN,CONN_PASS);
            meta = con.getMetaData();
            stat = con.createStatement();
            System.out.println("Connection to Store DB succesfull!");
            connected = true;
            return true;
        }
         catch(SQLException ex){
             System.out.println("Connection failed...");
             System.out.println(ex);
             DBDisconnect();
             connected = false;
             return(false);
         }
}

public boolean checkLogin(HttpServletRequest request) {
    try{
            final var userLogin = request.getParameter("username");
            final var userPass = request.getParameter("password");
            final String fileCountSQL = "select count() from usersfiles where user_id = '%s'";
            final String fileNamesSQL = "select usersfiles.fname FROM usersfiles where user_id = '%s'";
            if(!userLogin.isEmpty() && !userPass.isEmpty()) {
                UserModel tmpuser = new UserModel();
                List<UsersTable> tableResults = new ArrayList<>();
                final var table = stat.executeQuery(LOG_SQL);
                while(table.next()) {
                    final var tmpTableElem = new UsersTable();
                    tmpTableElem.login = table.getObject(1).toString();
                    tmpTableElem.pass = table.getObject(2).toString();
                    tmpTableElem.user_id = Integer.parseInt(table.getObject(3).toString());
                    tableResults.add(tmpTableElem);
                }
                //Ищем пользователя с соответствующими данными
                final var userSelectResult = tableResults.stream()
                        .filter(f->
                                f.login.equals(userLogin)
                                && f.pass.equals(userPass)
                        ).toList();
                if (userSelectResult.isEmpty()) {
                    return false;
                }
                final var loggedUser = userSelectResult.get(0);
                tmpuser.setID(loggedUser.user_id);
                tmpuser.setLog(loggedUser.login);
                tmpuser.setPass(loggedUser.pass);
                //Получаем количество файлов
                final var fileCountResult = stat.executeQuery(fileCountSQL.formatted(tmpuser.getID()));
                fileCountResult.next();
                final var filesCount = fileCountResult.getInt(1);
                
                //Получаем их имена
                final var userFiles = stat.executeQuery(fileNamesSQL.formatted(tmpuser.getID()));
                for(int i = 0; i < filesCount; i++) {
                    userFiles.next();
                    tmpuser.addFile(userFiles.getString(1));
                }
                if (tmpuser.getID() != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", tmpuser);
                    DBDisconnect();
                    return(true);
                }
                    return false ;
            }
    } catch(Exception ex) {
        System.out.println("Error in DBConn");
        System.out.println("Connection failed...");
        System.out.println(ex);
        DBDisconnect();
        return false;      
    }
    return false;
}

public boolean DBDisconnect()
{
    try {
        if (!connected) {
            return true;
        }
        connected = false;
        if (res != null) {
            res.close();
        }
        stat.close();
        con.close();
        con = null;
        meta = null;
        stat = null;
        res = null;
        DriverManager.deregisterDriver(mydriv);
        mydriv = null;
        System.out.println("Database disconnected");   
        return true;
    }
    catch(SQLException e){
        System.out.println("Error in DBDisconnect");       
        System.out.println(e.getMessage());
        return false;
    }
}

}
