<%-- 
    Document   : header
    Created on : 28 окт. 2023 г., 12:42:53
    Author     : Пажылой ай3
--%>

<%@page import="core.login.UserModel"%>
<%@page import="core.login.UserModel"%>
<%@page import="core.utils.SessionHelper"%>
<!DOCTYPE html>
<html>
    <h:head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Группа 220401</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/WEB-INF/css/Style.css"%></style>
    </h:head>
    <body>
    <h:body class = "body">
        <div class="header">
            <div class="blockLeft">
                <a class="link" href="/WebBD/">Главная</a>
            </div>
            <div class="blockCenter">
                Информационные Системы и Технологии
            </div>
            <div class="blockRight">
            <%
                boolean userLogged = SessionHelper.checkIsUserLogged(session);
                if  (!userLogged)
                {
            %>
                    <a class="link" href="/WebBD/Login">Вход</a>
            </div>
            <%
                }
                else
                {
                    final UserModel user = SessionHelper.getUserModelFromSession(session);
            %>
                    <%= user.getLogin()%>
                    <a class="link" href="LogOut">(выход)</a>
            <%
                }
            %>
        </div>
        <% 
                if (session.getAttribute("Message")!= null)
                {
        %>
                    <div class="alert alert-danger" role="alert">
                        <%=session.getAttribute("Message")%>
                    </div>
        <%
                    session.setAttribute("Message", null);
                }
        %>
    </body>
</html>
