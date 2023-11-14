/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import core.login.UserModel;
import core.utils.SessionHelper;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Пажылой ай3
 */
public class PageTemplateRenderer {
    public static String renderBaseHeader(HttpSession session) {
        UserModel currentUser;
        boolean logged;
        StringBuilder template = new StringBuilder();
        //Базовая часть
        template.append("""
                            <h1 class="header">
                                <div class="blockLeft">
                                    <a class="link" href="/WebBD/">Главная</a>
                                </div>
                                <div class="blockCenter">
                                    <p class="font-weight-bold">Информационные Системы и Технологии</p>
                                </div>
                                """);
        if (!SessionHelper.checkIsUserLogged(session)){
            template.append("""
                                <div class="blockRight">
                                    <a class="link" href="/WebBD/Login">Вход</a>
                                </div>"""
            );
        } else {
            logged = true;
            currentUser = SessionHelper.getUserModelFromSession(session);
            template
                    .append(currentUser.getLogin())
                    .append("""
                                <div class="blockLogout">
                                    <a class="link" href="LogOut">(Выход)</a>
                                </div>
                            </h1>"""
                    );
        }
        return template.toString();
    }
}
