/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.utils;

import core.login.UserModel;
import core.messages.MessageCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Пажылой ай3
 */
public class SessionHelper {
    public static boolean checkIsUserLogged(HttpSession session) {
        if ((UserModel)session.getAttribute("user") == null) {
            return false;
        }
        if(
            (session.getAttribute("logRes") == null) 
            || (Boolean.parseBoolean(
                session.getAttribute("logRes").toString()) == Boolean.FALSE
            )
        ) {
            return false;
        } 
        return true;
    }
    
    public static UserModel getUserModelFromSession(HttpSession session) {
        return (UserModel)session.getAttribute("user");
    }
    
    public static void checkWatchPermission(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (!checkIsUserLogged(session)) {
                throw new Exception();
            }
        } catch (Exception ex) {
            
            MessageCreator.getInstance()
                    .clearMessages()
                    .addMessage(
                    session, 
                    "Страница доступна только авторизированным пользователям"
            );
            request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
        }
    }
}
