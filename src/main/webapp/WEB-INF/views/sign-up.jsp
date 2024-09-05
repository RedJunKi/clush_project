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

    <p id="error-message" style="color: red; display: none;">회원가입에 실패했습니다. 다시 시도해 주세요.</p>

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
            .then(response => {
                if (response.ok) { // 회원가입 성공
                    return response.json();
                } else { // 회원가입 실패
                    throw new Error('회원가입 실패');
                }
            })
            .then(data => {
                alert('회원가입이 되었습니다.');
                window.location.href = '/login'; // 로그인 페이지로 리다이렉트
            })
            .catch((error) => {
                document.getElementById('error-message').style.display = 'block'; // 에러 메시지 표시
                console.error('Error:', error);
            });
        });

        document.getElementById('login').addEventListener('click', function() {
            window.location.href = '/login'; // 로그인 페이지로 이동
        });
    </script>
</body>
</html>
