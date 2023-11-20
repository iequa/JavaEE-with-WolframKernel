﻿<%-- 
    Document   : mathematica
    Created on : 31 окт. 2023 г., 11:51:54
    Author     : Пажылой ай3
--%>
<style><%@include file="/WEB-INF/css/math-input.css"%></style>
<!DOCTYPE html>
<html>
    <jsp:include page="header.jsp"></jsp:include>
    <body>
        <form method="post" action="${pageContext.request.contextPath}/mathematica/mathapi">
            <h3>Запрос</h3>
            <textarea 
                type="text" 
                name="inputstring" 
                class="math-input"
                required minlength="1"
            >${question}</textarea>
            <Button type="submit" name="submit">Запрос</button>
        </form>
        <div>
            <h3>Ответ ядра mathematica</h3>
            <textarea 
                type="text" 
                name="response-string" 
                class="math-input"
                readonly="true"
                >${answer}</textarea>
        </div>
    </body>
</html>
