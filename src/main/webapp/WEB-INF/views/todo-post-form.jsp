<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>할일 등록</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <header>
            <h2>할일 등록</h2>
        </header>
        <main id="todo-post-main">
            <div>
                <form id="addTodoForm">
                    <h4>어떤 일인가요?</h4>
                    <label for="title"></label>
                    <input type="text" id="title" placeholder="예: 자바 스크립트 공부" required>
                    <h4>자세히 설명해주세요</h4>
                    <label for="description"></label>
                    <input type="text" id="description" placeholder="예: 비동기 처리부분 공부하기" required>

                    <button type="button" id="backButton">이전</button>
                    <button type="submit">추가하기</button>
                    <button type="reset">내용 지우기</button>
                </form>
            </div>
        </main>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const token = localStorage.getItem('authToken'); // JWT 토큰 가져오기

                const backButton = document.getElementById('backButton');
                const addTodoForm = document.getElementById('addTodoForm');

                if (backButton) {
                    backButton.addEventListener('click', function() {
                        window.location.href = '/todos';
                    });
                }

                if (addTodoForm) {
                    addTodoForm.addEventListener('submit', function(event) {
                        event.preventDefault();

                        const title = document.getElementById('title').value;
                        const description = document.getElementById('description').value;

                        fetch('/api/todos', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${token}` // JWT 토큰을 헤더에 포함
                            },
                            body: JSON.stringify({
                                title: title,
                                description: description
                            })
                        })
                        .then(response => {
                            if (response.ok) {
                                alert('할일이 추가되었습니다.');
                                window.location.href = '/todos';
                            } else {
                                throw new Error('Error adding TODO');
                            }
                        })
                        .catch(error => console.error('Error:', error));
                    });
                }
            });

            function getMemberIdFromToken(token) {
                // 토큰에서 memberId를 추출하려면 서버 측에서 처리하는 것이 더 안전합니다.
                const payload = JSON.parse(atob(token.split('.')[1])); // JWT의 페이로드 디코딩
                return payload.memberId; // payload에서 memberId 추출
            }
        </script>
    </body>
</html>
