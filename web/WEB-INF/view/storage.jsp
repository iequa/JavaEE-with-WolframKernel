<%@page import="core.utils.SessionHelper"%>
<%@page import="core.login.UserModel"%>
<h:body class = "body">
        <h1 class="header">
            <div class="blockLeft">
                <a class="link" href="/WebBD/">???????</a>
            </div>
            <div class="blockCenter">
                <p class="font-weight-bold">?????????????? ??????? ? ??????????</p>
            </div>
            <div class="blockRight">
            <%
                boolean userLogged = SessionHelper.checkIsUserLogged(session);
                if  (!userLogged)
                {
                    request.getRequestDispatcher("/WebBD").forward(request, response);
                }
                else
                {
                    final UserModel user = SessionHelper.getUserModelFromSession(session);
            %>
                    <%= user.getLogin()%>
                    </div>
                    <div class="blockLogout">
                    <a class="link" href="LogOut">(?????)</a>
                    </div>   
            <%
                }
            %>
        </h1>
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
            <h1 style =" text-align: center" class="display-4">???????? ?????????</h1>
            <div class="Widget2">
                ???????? ???? ??? ????????: <br />
                <form action = "/WebBD/UploadServlet" method = "post"
                      enctype = "multipart/form-data">
                <input type = "file" name = "???????" size = "50" />
                <br />
                <input type = "submit" value = "?????????" />
                </form>
            </div>
            <div class= "RightWidget">
                <a class="link" href=
                "http://olymp.tsu.tula.ru/">
                ?????????? ??????? ????</a>
                <a class="link" href=
                "https://tulsu.ru/schedule/?group=220401">
                ?????????? ?????? ????</a>
            </div>
            <div class ="FilesBlock">
                <table class="table">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">??? ?????</th>
                    <th scope="col">????????</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <%
                    UserModel user = new UserModel();
                    user = SessionHelper.getUserModelFromSession(session);
                    if (user.getFileCount() > 0) {
                        for (int i = 0; i <  (user.getFileCount()); i++)
                        {
                %>
                        <tr>
                            <th scope="row"><%=i+1%></th>
                            <td><%=user.getFileName(i)%></td>
                            <td><a class="btn btn-primary" href="/WebBD/UploadServlet?load=<%=i%>">?????????</a></td>
                            <td><a class="btn btn-danger" href="/WebBD/UploadServlet?del=<%=i%>">???????</a></td>
                        </tr>
                <%  
                        }
                    }
                %>
                  </tbody>
                </table>
            </div>
    </h:body>