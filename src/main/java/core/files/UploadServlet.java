/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package core.files;

import core.login.UserModel;
import core.database.DBConn;
import core.messages.MessageCreator;
import core.utils.SessionHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

// Import required java libraries
 
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.annotation.WebListener;

@WebListener
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet {
   private static final DBConn connection = DBConn.getInstance();
   private final static String SYSTEM_NAME = System.getProperty("os.name").toLowerCase();

   private boolean isMultipart;
   private final String FILEPATH = SYSTEM_NAME.startsWith("win") ? 
           "E:\\Projects\\Java\\WebWolfram\\users_content\\"
           :
           "/opt/tomcat/user_files/"
           ;
   private final String FOLDER_SEPARATOR = SYSTEM_NAME.startsWith("win") ? 
           "\\"
           :
           "/"
           ;
           //"C:\\Users\\Artyom\\Desktop\\laby univer\\3 krs\\5 sem\\KURS BD\\WebBD\\Users_Content\\";
   private final int MAXFILESIZE = 5000000 * 1024;
   private final int MAXMEMSIZE = 5000000 * 1024;
   private File file ;
   private String Logged;
   
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // Check that we have a file upload request

   }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel user = (UserModel)request.getSession().getAttribute("user");
        System.out.println("User %s trying to send/delete file ".formatted(user.getLogin()));
        if (!CheckLog(request)) {
            return;
        }
        String fileName;
        boolean del = false;
        if(request.getParameter("load") != null) {
            fileName = user.getFileName(Integer.parseInt(request.getParameter("load")));
        }
        else {
            fileName = user.getFileName(Integer.parseInt(request.getParameter("del")));
            del = true;
        }
        if(fileName == null || fileName.isEmpty()) {
            MessageCreator.getInstance().addMessage(request.getSession(), "Имя файла не может быть пустым!");
            throw new ServletException("File Name can't be null or empty");
        }
        File file = new File(FILEPATH + user.getLogin() + File.separator + fileName);
        //Если файл существует на сервере, но его нет на носителе
        if(!file.exists()){
            final String sqlDelete = """
                    delete from \"usersfiles\" 
                    where usersfiles.user_id =  '%s'
                    and usersfiles.fname = '%s';"""
                    .formatted(
                        Integer.toString(user.getID()),
                        fileName
                     );
                if(connection.connected || connection.tryConn()) {
                    try {
                        connection.stat.execute(sqlDelete);
                    } catch (SQLException ex) {
                        Logger.getLogger(UploadServlet.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
            try {
                user.deleteFile(fileName);
            } catch (Exception ex) {
                Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            MessageCreator.getInstance().addMessage(request.getSession(), "Файл удалён.");
        }
        if (!del) {
            System.out.println("File location on server::" + file.getAbsolutePath());
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());
            response.setContentType(mimeType != null? mimeType:"application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read=0;
            while((read = fis.read(bufferData))!= -1){
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
            System.out.println("File downloaded at client successfully");
        }
        else {
            file.delete();
            final var sql = "DELETE FROM \"usersfiles\" WHERE usersfiles.user_id = '%s' AND usersfiles.fname = '%s';";
            try {
                if(connection.connected || connection.tryConn()) {
                    connection.stat.execute(sql.formatted(Integer.toString(user.getID()), fileName));
                }
                user.deleteFile(Integer.parseInt(request.getParameter("del")));
                request.getSession().setAttribute("user", user);
                System.out.println("File deleted on server successfully");
            } catch (Exception ex) {
                Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect("/WebBD/filestorage");
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
      System.out.println("Trying to get file from user %s"
              .formatted(SessionHelper.getUserModelFromSession(request.getSession()).getLogin())
      );
      if (!CheckLog(request)) {
          MessageCreator.getInstance().addMessage(request.getSession(), "Пользователь не выполнил вход!");
          response.sendRedirect("/WebBD/");
          return;
      }
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
      
      if( !isMultipart ) {
          MessageCreator.getInstance().addMessage(request.getSession(), "No file uploaded");
      }
  
      DiskFileItemFactory factory = new DiskFileItemFactory();
   
      // maximum size that will be stored in memory
      factory.setSizeThreshold(MAXMEMSIZE);
   
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File(FILEPATH));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
   
      // maximum file size to be uploaded.
      upload.setSizeMax( MAXFILESIZE );

      try { 
         // Parse the request to get fileS items.
         List fileItems = upload.parseRequest(request);
         // Process the uploaded file items
         Iterator i = fileItems.iterator();
         UserModel user = (UserModel)request.getSession().getAttribute("user");
         while ( i.hasNext ()) {
            FileItem fi = (FileItem)i.next();
            for (int j = 0; j < user.getFileCount(); j++) {
                if (user.getFileName(j).equals(fi.getName())) {
                    System.out.println("Error! File already exists on server");
                    request.getSession().setAttribute("Message", 
                            "Файл с таким именем уже находится на сервере!");
                    response.sendRedirect("/WebBD/");
                    return;
                }
            }
            
            if (fi.getName().isEmpty()) {
                request.getSession().setAttribute("Message", "Файл не выбран!");
                throw new Exception("no file");
            }
            if ( !fi.isFormField()) {
               // Get the uploaded file parameters
               String fieldName = fi.getFieldName();
               String fileName = fi.getName();
               String contentType = fi.getContentType();
               boolean isInMemory = fi.isInMemory();
               long sizeInBytes = fi.getSize();
            
               // Write the file
               if( fileName.lastIndexOf(FOLDER_SEPARATOR) >= 0 ) {
                  file = new File( FILEPATH + user.getLogin() + FOLDER_SEPARATOR
                          + fileName.substring( fileName.lastIndexOf(FOLDER_SEPARATOR)));
               } else {
                  file = new File( FILEPATH + user.getLogin() + FOLDER_SEPARATOR
                          + fileName.substring(fileName.lastIndexOf(FOLDER_SEPARATOR)+1));
               }
               fi.write(file);
            }
        }
            user.addFile(file.getName());
            //user.addFile((Integer)request.getSession().getAttribute("u.fCount"));
            final String insertSqlString = "INSERT INTO usersfiles (user_id, fname) VALUES ( '%s', '%s')";
            if(connection.connected && connection.tryConn()) {
                connection.stat.execute(insertSqlString.formatted(
                    user.getID(),file.getName())
                );
                request.getSession().setAttribute("user", user);
            }
         } catch(Exception ex) {
            System.out.println("Error in UploadServlet");
            System.out.println(ex);
            if (ex.getMessage().contains("the request was rejected because its size")) {
                MessageCreator.getInstance().addMessage(request.getSession(),
                        "Файл имеет слишком большой размер"
                );
                return;
            }
         }
        response.sendRedirect("/WebBD/filestorage");
    }
    
    protected static boolean CheckLog(HttpServletRequest request) {
        final String logRes = request.getSession().getAttribute("logRes").toString();
        if(logRes == null || Boolean.parseBoolean(logRes) == Boolean.FALSE) {
            System.out.println("User not logged");
            return false;
        }
        return true;
   }
}
