<%-- 
    Document   : LoginPage
    Created on : 22.11.2022, 21:12:07
    Author     : Artyom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Группа 220401</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/WEB-INF/css/Style.css"%></style>
    </head>
    <body>
        <h1 class="header">
            <div class="blockLeft">
                <a class="link" href="/WebBD/">Главная</a>
            </div>
            <div class="blockCenter">
                ИСИТ
            </div>
            <div class="blockRight">
                <a class="link" href="/WebBD/Login">Вход</a>
            </div>
        </h1>
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
