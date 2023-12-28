<%@page import="core.login.UserModel"%>
<%@page import="core.login.UserModel"%>
<%@page import="core.utils.SessionHelper"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Группа 220401</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style><%@include file="/WEB-INF/css/Style.css"%></style>
        
        <div class="header">
            <div class="blockLeft">
                <a class="link" href="/WebBD/">Главная</a>
            </div>
            <div class="blockCenter">
                <h1 class="mainText">Информационные Системы и Технологии</h1>
            </div>
            <div class="blockRight">
            <%
                boolean userLogged = SessionHelper.checkIsUserLogged(session);
                if  (!userLogged)
                {
            %>
                    <a class="link" href="/WebBD/Login">Вход</a>
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
                </div>
                <div class="message">
                    <div class="alert alert-danger" role="alert">
                        <%=session.getAttribute("Message")%>
                    </div>
                </div>
        <%
                    session.setAttribute("Message", null);
                }
        %>
    </div>
    </head>
