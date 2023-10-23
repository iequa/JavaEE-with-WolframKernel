/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package core.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import core.database.DBConn;
import core.messages.MessageCreator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;


//import WebBD.business.;
/**
 *
 * @author Artyom
 */
@WebListener
@WebServlet(name = "LoginCheck", urlPatterns = {"/LoginCheck"})
public class LoginCheck extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final DBConn connection = new DBConn();
    private String login;
    private String pass;
    private boolean loginResult;
    
public boolean checkLogin()
{
    try{
        if (login.isEmpty() || pass.isEmpty()) {
            return false;
        }
            final String SQL = "select * from users";
            List<UsersTable> resSQL = new ArrayList<>();
            connection.res = connection.stat.executeQuery(SQL);
            while(connection.res.next())
            {
                resSQL.add((UsersTable)connection.res.getObject(1));
            }
            final var loginResult = resSQL.stream()
                    .filter(f-> f.login.equals(login) && f.pass.equals(pass))
                    .toList();
            return (!loginResult.isEmpty() && loginResult.size() == 2);
        } catch (SQLException ex) {
            System.out.println("Login/pass failure");
            System.out.println(ex); 
            return false;
        }
}

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
            connection.tryConn();
            loginResult = connection.checkLogin(request);
            final UserModel user = (UserModel) request.getSession().getAttribute("user");
            if(loginResult == true) {
                request.getSession().setAttribute("logRes", loginResult);
                try {
                    response.sendRedirect("/WebBD/");
                }
                catch(IOException e) {
                    connection.DBDisconnect();
                    System.out.println("Error in LoginCheck");      
                    System.out.println(e);
                }
            } else {
                boolean res = false;
                connection.DBDisconnect();
                request.getSession().setAttribute("logRes", res);
                MessageCreator.addMessage("Неверный логин/Пароль");
                //request.getSession().setAttribute("Message", "Неверный логин/Пароль");
                response.sendRedirect("/WebBD/");
            }
        }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
