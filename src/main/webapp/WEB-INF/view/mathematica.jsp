<%@page import="java.util.Base64"%>
<%@page import="core.utils.SessionHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%//Не позволяем просматривать без авторизации
    SessionHelper.checkWatchPermission(session, request, response);
%>
<!DOCTYPE html>
<html>
    <head>
        <style><%@include file="/WEB-INF/css/math-input.css"%></style>
    </head>
    <jsp:include page="header.jsp"/>
    <body>
        <jsp:include page="menu.jsp"/>
        <p class="lead">Wolfram mathematica lite</p>
        <div class="math-input-div">
            <form method="post" action="${pageContext.request.contextPath}/mathematica/mathapi">
                <h3 class="math-input-title-operation-label">Запрос</h3>
                <textarea 
                    type="text" 
                    name="inputstring" 
                    class="math-input"
                    required minlength="1"
                    title="Поле для ввода запроса"
                >${question}</textarea>
                <div class="checkbox">
                    Результат выполнения является графическим?
                    <input name="graphicRes" type="checkbox"/>
                </div>
                <Button class="math-input-confirm-button" type="submit" name="submit">Запрос</button>
            </form>
        </div>
        <div>
            <h3 class="math-input-title-operation-label">Ответ ядра mathematica</h3>
            <% 
                final String check = request.getParameter("graphicRes");
                if (check!= null && check.equals("on"))
                {
                    final byte[] answer = (byte[])request.getAttribute("answer");
                    String fileBase64 = Base64.getEncoder().encodeToString(answer);
                    out.println("<img style=\"max-width: 70%%; height: auto; margin-left: var(--header-block-width); margin-right: var(--header-block-width);display: flex;\" src=\"data:image/png;base64,%s\"/>".formatted(fileBase64));
            %>
            <%} else { %>
            <textarea 
                type="text" 
                name="response-string" 
                class="math-input"
                readonly="true"
                >${answer}</textarea>
            <%}%>
        </div>
    </body>
</html>
