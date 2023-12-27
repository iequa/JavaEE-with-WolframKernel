/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package core.mathapi;

import core.files.UploadServlet;
import core.utils.SessionHelper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.firstproject.kernelwrapper.KernelLinkWrapper;

/**
 *
 * @author Пажылой ай3
 */
public class MathApi extends HttpServlet {
    private String inputString = "";
    private String evaluateFileIndex = "";
    private final static String LINUX_EOL = "\r";
    private final static String WINDOWS_EOL = "\r\n";
    private final static String MACOS_EOL = "\n";
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
        inputString = request.getParameter("inputstring");
        evaluateFileIndex = request.getParameter("file");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if (inputString != null && !inputString.isEmpty()) {
                final var parsedLines = parseStringToList(inputString);
                final var answer = KernelLinkWrapper.evaluateString(parsedLines);
                request.setAttribute("question", inputString);
                request.setAttribute("answer", answer);
                request.getRequestDispatcher("/mathematica").forward(request, response);
            } else if (evaluateFileIndex != null && !evaluateFileIndex.isEmpty()) {
                final var user = SessionHelper.getUserModelFromSession(request.getSession());
                final var nbExpr = "Get[FileNameJoin[{\"%s%s\", \"%s\"}]]".formatted(
                        UploadServlet.FILEPATH,    
                        user.getLogin(),
                        user.getFileName(Integer.parseInt(evaluateFileIndex))
                );
                final var answer = KernelLinkWrapper.evaluateString(List.of(nbExpr));
                request.setAttribute("question", "evaluated file '%s'".formatted(user.getFileName(Integer.parseInt(evaluateFileIndex))));
                request.setAttribute("answer", answer);
                request.getRequestDispatcher("/mathematica").forward(request, response);
            }
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

    private List <String> parseStringToList(String mainString) {
        final var lines = new ArrayList<String>();
        mainString.lines()
                .filter(line->!line.isEmpty())
                .forEach(line->lines.add(line))
        ;
        return lines;
    }
}
