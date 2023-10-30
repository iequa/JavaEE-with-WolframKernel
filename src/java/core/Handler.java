/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package core;

import core.login.LoginCheck;
import core.messages.MessageCreator;
import core.utils.SessionHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Artyom
 */
@WebListener
public class Handler extends HttpServlet {
    public static String MAINPAGE = "/WebBD/";
    public static String LOGINPAGE = "/WebBD/Login";
    public static String STORAGEPAGE = "/WebBD/filestorage";
    public static String MATHEMATICAPAGE = "/WebBD/mathematica";
    boolean logged = false;
    MessageCreator messageCreator;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logged = false;
        if (request.getSession(true).getAttribute("logRes") != null) {
            logged = Boolean.parseBoolean(request.getSession().getAttribute("logRes").toString());
        }
        String uri = request.getRequestURI();
        if (logged && request.getSession().getAttribute("u") == null) {
            request.removeAttribute("logRes");
        }
        if (uri.equals(STORAGEPAGE)) {
            request.getRequestDispatcher("/WEB-INF/view/storage.jsp").forward(request, response);
        }
        if (uri.equals(LOGINPAGE)) {
            request.getRequestDispatcher("/WEB-INF/view/LoginPage.jsp").forward(request, response);
        }
        if (uri.equals(MATHEMATICAPAGE)) {
            request.getRequestDispatcher("/WEB-INF/view/mathematica.jsp").forward(request, response);
        }
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

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
