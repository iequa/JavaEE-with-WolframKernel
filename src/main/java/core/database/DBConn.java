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
import core.login.DBUserModel;
import core.messages.MessageCreator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Artyom
 */
public class DBConn {
    private static DBConn instance;
    public static boolean connected;
    private final static String CONN_LOGIN = "iequa";
    private final static String CONN_PASS = "postgres";
    //private final static String CONN_URL = "jdbc:sqlanywhere:kursbd";
    private final static String CONN_URL = "jdbc:postgresql://localhost:5432/sitedata";
    
    public Statement stat;
    public static DatabaseMetaData meta;
    public static Connection con;
    public ResultSet res;
    private Driver mydriv;
    private final static String LOG_SQL = "select * from public.\"users\"";
    
    private DBConn() {
        tryConn();
    }
    
    public static synchronized DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }
    
    public boolean tryConn(){
        if (connected) {
            return true;
        }
        try {
                //mydriv = new sap.jdbc4.sqlanywhere.IDriver();
                //Class.forName("sap.jdbc4.sqlanywhere.IDriver");
                //if (DriverManager.drivers().noneMatch(drv->drv.equals(mydriv))) {
                //    DriverManager.registerDriver(mydriv);
                //}
                con = DriverManager.getConnection(CONN_URL,CONN_LOGIN,CONN_PASS);
                meta = con.getMetaData();
                stat = con.createStatement();
                System.out.println("Connection to Store DB succesfull!");
                connected = true;
                DriverManager.setLoginTimeout(10000);
                return true;
            }
             catch(Exception ex){
                 System.out.println("Connection failed...");
                 System.out.println(ex);
                 connected = false;
                 return(false);
             }
    }

    public boolean checkLogin(HttpServletRequest request) {
        try{
                final var userLogin = request.getParameter("username");
                final var userPass = request.getParameter("password");
                final String fileCountSQL = "select count(*) from public.\"usersfiles\" where user_id = '%s'";
                final String fileNamesSQL = "select \"usersfiles\".fname from public.\"usersfiles\" where user_id = '%s'";
                if(!userLogin.isEmpty() && !userPass.isEmpty()) {
                    UserModel tmpuser = new UserModel();
                    List<DBUserModel> tableResults = new ArrayList<>();
                    final var table = stat.executeQuery(LOG_SQL);
                    while(table.next()) {
                        final var tmpTableElem = new DBUserModel();
                        tmpTableElem.user_id = Integer.parseInt(table.getObject(1).toString());
                        tmpTableElem.login = table.getObject(2).toString();
                        tmpTableElem.pass = table.getObject(3).toString();
                         
                        tableResults.add(tmpTableElem);
                    }
                    //Ищем пользователя с соответствующими данными
                    final DBUserModel user = tableResults.stream()
                            .filter(f -> f.login.equals(userLogin))
                            .filter(f -> f.pass.equals(userPass))
                            .findFirst()
                            .orElseThrow();
                    tmpuser.setID(user.user_id);
                    tmpuser.setLog(user.login);
                    tmpuser.setPass(user.pass);
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
                        return(true);
                    }
                        return false;
                }
        } catch(Exception ex) {
            System.out.println("Error in DBConn handle");
            System.out.println(ex);
            MessageCreator.getInstance().addMessage(request.getSession(), ex.getMessage());
            return false;      
        }
        return false;
    }

    public boolean dbDisconnect() {
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
