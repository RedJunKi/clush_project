<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TODO Application</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>나의 해야할 일들</h1>
            <button id="addTodo">새로운 TODO 등록</button>
        </header>
        <main>
            <!-- Pending Column -->
            <div class="column todo">
                <h2>TODO</h2>
                <div id="todoColumn"></div>
            </div>

            <!-- In Progress Column -->
            <div class="column doing">
                <h2>DOING</h2>
                <div id="doingColumn"></div>
            </div>

            <!-- Done Column -->
            <div class="column done">
                <h2>DONE</h2>
                <div id="doneColumn"></div>
            </div>
        </main>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const addTodoButton = document.getElementById('addTodo');

            if (addTodoButton) {
                addTodoButton.addEventListener('click', function() {
                    window.location.href = '/todos/add';
                });
            }
        });
    </script>

    <script type="text/javascript" src= "../js/script.js"></script>
</body>
</html>