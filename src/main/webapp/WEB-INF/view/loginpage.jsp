<%-- 
    Document   : LoginPage
    Created on : 22.11.2022, 21:12:07
    Author     : Artyom
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="header.jsp"/>
    <body>
        <p class="BigText">Вход в учётную запись<p>
        <div class="LoginPage">
            <form method="post" action="/WebBD/LoginCheck">
            <p><label class="LoginLabels">Логин : </label> 
            <input class="InputBox" type="text" placeholder="Логин" name="username" required></p>    
            <p><label class="LoginLabels">Пароль : </label>
            <input class="InputBox" type="password" placeholder="Введите пароль" name="password" required> </p>   
            <button type="submit">Login</button>
            <input type="checkbox" checked="checked"> Запомнить
            <a style ="display: block" href="https://vk.com/esomm"> Забыли пароль? </a>
            </form>
        </div>
    </body>
</html>
