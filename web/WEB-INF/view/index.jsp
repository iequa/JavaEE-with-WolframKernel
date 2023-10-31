
<%@page import="core.utils.SessionHelper"%>
<%@page import="core.PageTemplateRenderer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="core.login.UserModel"%>
<%@page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    function actionResolver(actName) {
        if (actName === 'filestorage') {
            window.location=actName;
        }
        if (actName === 'mathematica') {
            window.location=actName;
        }
    }
</script>
<html>
    <jsp:include page="header.jsp"/>
        <%
            boolean userLogged = SessionHelper.checkIsUserLogged(session);
            if(!userLogged)
            {
        %>
            <div style="margin-bottom: 0px; padding-bottom: 20px" class="jumbotron">
                <h1 class="display-4">Группа 220401</h1>
                <p class="lead">Лучшие в своём деле.</p>
                <hr class="my-4">
                <p>Данный сайт - курсовой проект по дисциплине "Базы данных".</p>
            </div>
        <%
            }
            else
            {
        %>
                <h1 style ="text-align: center" class="display-4">Файловое хранилище</h1>
        <%
            }
            if(!userLogged)
            {
        %>
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
        <%
            }
            else
            {
        %>
            <div class="Widget2">
                Выберите файл для загрузки: <br/>
                <form action = "/WebBD/UploadServlet" method = "post"
                      enctype = "multipart/form-data">
                <input type = "file" name = "Открыть" size = "50" />
                <br />
                <input type = "submit" value = "Загрузить" />
                </form>
            </div>
            <div class= "RightWidget">
                <a class="link" href=
                "http://olymp.tsu.tula.ru/">
                Расписание старого типа</a>
                <a class="link" href=
                "https://tulsu.ru/schedule/?group=220401">
                Расписание нового типа</a>
            </div>
            <div>
                <button class="btn btn-primary" onclick="actionResolver('filestorage')">Файлики</button>
                <button class="btn btn-danger" onClick="actionResolver('mathematica')">Wolfram Mathematica</button>
        <%  
            }
        %>
            </div>
    </h:body>
</html>