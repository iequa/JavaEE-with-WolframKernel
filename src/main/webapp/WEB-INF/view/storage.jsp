<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="core.utils.SessionHelper"%>
<%@page import="core.login.UserModel"%>
<%//Не позволяем просматривать без авторизации
    SessionHelper.checkWatchPermission(session, request, response);
%>
<!DOCTYPE html>
<html>
    <jsp:include page="header.jsp"/>
    <body class = "body">
        <jsp:include page="menu.jsp"/>
        <p class="lead">Файловое хранилище</p>
            <div class ="FilesBlock">
                <div class="Widget2">
                    <form action = "/WebBD/UploadServlet" method = "post"
                          enctype = "multipart/form-data">
                        Выберите файл для загрузки:
                        <input type = "file" name = "Открыть" size = "50" />
                        <input type = "submit" value = "Загрузить" />
                    </form>
                </div>
                <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Название файла</th>
                    <th scope="col">Действия</th>
                  </tr>
                </thead>
                <% 
                    UserModel user = new UserModel();
                    user = (UserModel)request.getSession().getAttribute("user");
                    if (user.getFileCount() > 0) {
                        for (int i = 0; i < (user.getFileCount()); i++)
                        {
                            if (user.getFileName(i).endsWith(".nb"))
                %>
                        <tr>
                            <th scope="row"><%=i + 1%></th>
                            <td><%=user.getFileName(i)%></td>
                            <td>
                <%
                        if (user.getFileName(i).endsWith(".nb") || user.getFileName(i).endsWith(".m")) {
                %>
                            <form class="table-act-form" method="post" action="${pageContext.request.contextPath}/mathematica/mathapi?file=<%=i%>" >
                                <button class="btn btn-secondary">Запустить в Wolfram lite</button>
                            </form>
                <%
                        }
                %>
                                <a class="btn btn-primary" href="/WebBD/UploadServlet?load=<%=i%>">Загрузить</a>
                                <a class="btn btn-danger" href="/WebBD/UploadServlet?del=<%=i%>">Удалить</a>
                            </td>
                        </tr>
                <%  
                        }
                    }
                %>
                  </tbody>
                </table>
            </div>
    </body>
</html>