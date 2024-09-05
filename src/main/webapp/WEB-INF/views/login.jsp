<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
</head>
<body>
    <h2>로그인</h2>
    <form id="login-form">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">로그인</button>
        <button id="sign-up" type="button">회원가입</button>
    </form>

    <p id="error-message" style="color: red; display: none;">로그인에 실패했습니다. 다시 시도해 주세요.</p>

    <script>
        document.getElementById('login-form').addEventListener('submit', function(event) {
            event.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            const data = {
                email: email,
                password: password
            };

            fetch('/api/members/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (response.ok) { // 로그인 성공
                    return response.json();
                } else { // 로그인 실패
                    throw new Error('로그인 실패');
                }
            })
            .then(data => {
                alert('로그인 되었습니다.');
                window.location.href = '/'; // 메인 페이지로 리다이렉트
            })
            .catch((error) => {
                document.getElementById('error-message').style.display = 'block'; // 에러 메시지 표시
                console.error('Error:', error);
            });
        });

        document.getElementById('sign-up').addEventListener('click', function() {
            window.location.href = '/signup'; // 회원가입 페이지로 이동
        });
    </script>
</body>
</html>
