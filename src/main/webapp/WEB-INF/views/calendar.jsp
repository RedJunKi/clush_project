<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>달력 페이지</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>일정 관리 달력</h1>
            <div>
                <label for="yearSelect">년도:</label>
                <select id="yearSelect"></select>

                <label for="monthSelect">월:</label>
                <select id="monthSelect"></select>

                <button id="addEventButton">일정 추가</button>
            </div>
        </header>
        <main>
            <div id="calendar"></div>
        </main>
    </div>

    <script src="../js/calendar.js"></script>

    <style>
        .calendar-row {
            display: flex;
        }

        .calendar-cell {
            width: 100px;
            height: 100px;
            border: 1px solid #ccc;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            position: relative;
        }

        .calendar-cell .event-label {
            background-color: #f0c14b;
            border-radius: 4px;
            padding: 2px;
            margin-top: 5px;
            font-size: 12px;
            color: #333;
            position: absolute;
            bottom: 5px;
            width: 80%;
            text-align: center;
        }

        .empty {
            background-color: #f9f9f9;
        }
    </style>
</body>
</html>
