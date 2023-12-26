/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package core;

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
    public static final String MAINPAGE = "/WebBD/";
    public static final String LOGINPAGE = "/WebBD/Login";
    public static final String STORAGEPAGE = "/WebBD/filestorage";
    public static final String MATHEMATICAPAGE = "/WebBD/mathematica";
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
        boolean logged = false;
        response.setCharacterEncoding("UTF-8");
        if (request.getSession(true).getAttribute("logRes") != null) {
            logged = Boolean.parseBoolean(request.getSession().getAttribute("logRes").toString());
        }
        String uri = request.getRequestURI();
        if (logged && request.getSession().getAttribute("u") == null) {
            request.removeAttribute("logRes");
        }
        System.out.println("Navigate to... %s".formatted(uri));
        switch (uri) {
            case STORAGEPAGE->navigate("storage.jsp", request, response);
            case LOGINPAGE->navigate("loginpage.jsp", request, response);
            case MATHEMATICAPAGE -> navigate("mathematica.jsp", request, response);
            default->navigate("index.jsp", request, response);
        }
    }
    
    private void navigate(String target, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/%s".formatted(target)).forward(request, response);
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
