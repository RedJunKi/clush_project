<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
    <h2>회원가입</h2>
    <form id="signup-form">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br><br>

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">회원가입</button>
        <button id="login" type="button">로그인</button>
    </form>

    <script>
        document.getElementById('signup-form').addEventListener('submit', function(event) {
            event.preventDefault();

            const email = document.getElementById('email').value;
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            const data = {
                email: email,
                username: username,
                password: password
            };

            fetch('/api/members', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                alert('회원가입이 되었습니다.');
                window.location.href = '/login';
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        });

        document.getElementById('login').addEventListener('click', function() {
            window.location.href = '/login';
        });
    </script>
</body>
</html>