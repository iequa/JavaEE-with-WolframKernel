<%-- 
    Document   : mathematica
    Created on : 31 окт. 2023 г., 11:51:54
    Author     : Пажылой ай3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style><%@include file="/WEB-INF/css/math-input.css"%></style>
<!DOCTYPE html>
<html>
    <jsp:include page="header.jsp"></jsp:include>
    <body>
        <h1>Hello World!</h1>
        <form action="mathematica/mathapi" method="GET">
            <label>Hello World2!</label>
            <input 
                type="text" 
                name="input-wolfram" 
                class="math-input"
                required minlength="1"
            />
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>
