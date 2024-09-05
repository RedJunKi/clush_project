<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>일정 추가</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <header>
            <h2>일정 추가</h2>
        </header>
        <main id="event-post-main">
            <div>
                <form id="addEventForm">
                    <h4>일정 제목</h4>
                    <input type="text" id="title" placeholder="일정 제목" required>

                    <h4>일정 설명</h4>
                    <input type="text" id="description" placeholder="일정 설명" required>

                    <h4>시작 날짜</h4>
                    <input type="datetime-local" id="startDate" required>

                    <h4>종료 날짜</h4>
                    <input type="datetime-local" id="endDate" required>

                    <button type="button" id="backButton">이전</button>
                    <button type="submit">일정 추가</button>
                    <button type="reset">내용 지우기</button>
                </form>
            </div>
        </main>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const backButton = document.getElementById('backButton');
                const addEventForm = document.getElementById('addEventForm');

                // 이전 버튼 클릭 시 달력 페이지로 이동
                backButton.addEventListener('click', function() {
                    window.location.href = '/calendars';
                });

                // 일정 추가 폼 제출 시
                addEventForm.addEventListener('submit', function(event) {
                    event.preventDefault();

                    const title = document.getElementById('title').value;
                    const description = document.getElementById('description').value;
                    const startDate = document.getElementById('startDate').value;
                    const endDate = document.getElementById('endDate').value;

                    // 서버로 일정 데이터 전송
                    fetch('/api/calendars', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            title: title,
                            description: description,
                            startDate: startDate,
                            endDate: endDate
                        })
                    })
                    .then(response => {
                        if (response.ok) {
                            alert('일정이 추가되었습니다.');
                            window.location.href = '/calendars';
                        } else {
                            throw new Error('일정 추가 실패');
                        }
                    })
                    .catch(error => console.error('Error:', error));
                });
            });
        </script>
    </body>
</html>
