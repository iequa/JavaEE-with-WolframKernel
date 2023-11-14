/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.utils;

import core.login.UserModel;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Пажылой ай3
 */
public class SessionHelper {
    public static boolean checkIsUserLogged(HttpSession session) {
        if(
                (session.getAttribute("logRes") == null) 
                ||
                (Boolean.parseBoolean(
                    session.getAttribute("logRes").toString()) == Boolean.FALSE
                )
        )
        {
            return false;
        } else {
            return true;
        }
    }
    
    public static UserModel getUserModelFromSession(HttpSession session) {
        return (UserModel)session.getAttribute("user");
    }
}
