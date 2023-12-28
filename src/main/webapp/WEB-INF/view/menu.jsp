<%-- 
    Document   : menu
    Created on : 28 дек. 2023 г., 15:57:37
    Author     : Artyo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
    function actionResolver(actName) {
        window.location=actName;
    }
</script>
<div class="menu-block">
    <button class="btn btn-primary" onclick="actionResolver('filestorage')">
    Файловое хранилище
    </button>
    <button class="btn btn-danger" onClick="actionResolver('mathematica')">
    Wolfram Mathematica lite
    </button>
    <button class="btn btn-primary" onClick="window.open('https://tulsu.ru/schedule/?group=220401');">
    Расписание занятий
    </button>
</div>
