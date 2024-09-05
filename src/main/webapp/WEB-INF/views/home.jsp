<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>홈</title>
</head>
<body>
    <h2>To Do</h2>
    <button id="go-todo">To Do App</button>

    <h2>Calendar</h2>
    <button id="go-calendar">Calendar App</button>

    <script>
        document.getElementById('go-todo').addEventListener('click', function() {
            window.location.href = '/todos';
        });

        document.getElementById('go-calendar').addEventListener('click', function() {
            window.location.href = '/calendars';
        });
    </script>
</body>
</html>
