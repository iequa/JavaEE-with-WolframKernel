<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="core.utils.SessionHelper"%>
<%@page import="core.login.UserModel"%>
<!DOCTYPE html>
<html>
<h:body class = "body">
    <jsp:include page="header.jsp"/>
            <h1 style="text-align:center" class="display-4"></h1>
            <div class="Widget2">
                Выберите файл для загрузки: <br/>
                <form action = "/WebBD/UploadServlet" method = "post"
                      enctype = "multipart/form-data">
                <input type = "file" name = "Открыть" size = "50" />
                <br/>
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
            <div class ="FilesBlock">
                <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Название файла</th>
                    <th scope="col">Действия</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <% 
                    UserModel user = new UserModel();
                    user = (UserModel)request.getSession().getAttribute("user");
                    if (user.getFileCount() > 0) {
                        for (int i = 0; i < (user.getFileCount()); i++)
                        {
                %>
                        <tr>
                            <th scope="row"><%=i + 1%></th>
                            <td><%=user.getFileName(i)%></td>
                            <td><a class="btn btn-primary" href="/WebBD/UploadServlet?load=<%=i%>">Загрузить</a></td>
                            <td><a class="btn btn-danger" href="/WebBD/UploadServlet?del=<%=i%>">Удалить</a></td>
                        </tr>
                <%  
                        }
                    }
                %>
                  </tbody>
                </table>
            </div>
    </h:body>
</html>