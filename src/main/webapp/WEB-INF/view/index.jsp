
<%@page import="core.utils.SessionHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="core.login.UserModel"%>
<%@page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <jsp:include page="header.jsp"/>
    <body>
        <%
            //<img class="imgmain" src="<c:url value="/ImageServlet?imageName=group.jpg"/>"/>
            boolean userLogged = SessionHelper.checkIsUserLogged(session);
            if(!userLogged)
            {
        %>
            <div class="group-header">
                <h1 class="display-4">Группа 220401</h1>
                <p class="lead">Лучшие в своём деле.</p>
                <hr class="my-4">
                <p>Данный сайт - курсовой проект.</p>
            </div>
            <div class="studlist">
                <p style="margin-left: 5px" class="font-weight-bold">Список студентов:</p>    
                <UL class="list-group">
                    <li class="list-group-item list-group-item-dark">Агаджанова Софья</li>
                    <li class="list-group-item list-group-item-dark">Акимова Елизавета</li>
                    <li class="list-group-item list-group-item-dark">Бажин Максим</li>
                    <li class="list-group-item list-group-item-dark">Евдокишина Александра</li>
                    <li class="list-group-item list-group-item-dark">Калинин Артём</li>
                    <li class="list-group-item list-group-item-dark">Ковалев Андрей</li>
                    <li class="list-group-item list-group-item-dark">Мшецян Николай</li>
                    <li class="list-group-item list-group-item-dark">Поляков Даниил</li>
                    <li class="list-group-item list-group-item-dark">Рассохина Олеся </li>
                    <li class="list-group-item list-group-item-dark">Сафронова Ольга</li>
                    <li class="list-group-item list-group-item-dark">Шарапов Александр</li>
                </UL>
            </div>
            <h2>Наша группа</h2>
            
            
        <%
            }
            else
            {
        %>
            <p class="lead">Меню</p>
            <jsp:include page="menu.jsp"/>
            <hr class="my-4">
            <p>Работа с файловым хранилищем позволяет использовать данный сайт как сервер
            для хранения данных. Данные каждого пользователя хранятся отдельно.</p>
            <p>Wolfram mathematica lite позволяет выполнять простые функции, доступные
            в ядре mathematica, от сложения до вычисления функций</p>
        <%  
            }
        %>
    </body>
</html>