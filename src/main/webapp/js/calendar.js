document.addEventListener('DOMContentLoaded', function() {
    const calendarContainer = document.getElementById('calendar');
    const yearSelect = document.getElementById('yearSelect');
    const monthSelect = document.getElementById('monthSelect');

    // 현재 날짜 정보
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth() + 1;

    // 연도 선택 동적으로 생성 (지난 5년부터 앞으로 5년)
    for (let year = currentYear - 5; year <= currentYear + 5; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        if (year === currentYear) option.selected = true;
        yearSelect.appendChild(option);
    }

    // 월 선택 동적으로 생성 (1월부터 12월까지)
    for (let month = 1; month <= 12; month++) {
        const option = document.createElement('option');
        option.value = month;
        option.textContent = month;
        if (month === currentMonth) option.selected = true;
        monthSelect.appendChild(option);
    }

    // 달력 생성 함수
    function generateCalendar(year, month) {
        calendarContainer.innerHTML = ''; // 기존 달력 초기화
        const firstDay = new Date(year, month - 1, 1).getDay(); // 월의 첫날 요일
        const lastDate = new Date(year, month, 0).getDate(); // 월의 마지막 날짜

        let row = document.createElement('div');
        row.classList.add('calendar-row');
        calendarContainer.appendChild(row);

        // 빈 공간 채우기 (해당 월의 첫 요일까지)
        for (let i = 0; i < firstDay; i++) {
            const emptyCell = document.createElement('div');
            emptyCell.classList.add('calendar-cell', 'empty');
            row.appendChild(emptyCell);
        }

        // 날짜 셀 채우기
        for (let day = 1; day <= lastDate; day++) {
            const cell = document.createElement('div');
            cell.classList.add('calendar-cell');
            cell.dataset.date = `${year}-${month}-${day}`; // 날짜 정보 저장
            cell.textContent = day;
            row.appendChild(cell);

            // 줄 바꿈 처리
            if ((firstDay + day) % 7 === 0) {
                row = document.createElement('div');
                row.classList.add('calendar-row');
                calendarContainer.appendChild(row);
            }
        }

        // 일정 불러와서 표시
        const startDate = new Date(year, month - 1, 1).toISOString().split('.')[0]; // `Z` 제거
        const endDate = new Date(year, month, 0).toISOString().split('.')[0]; // `Z` 제거
        fetchCalendarEvents(startDate, endDate);
    }

    // 일정 불러오기 함수
    function fetchCalendarEvents(start, end) {
        // API 요청에 시작일과 종료일을 쿼리 파라미터로 전달
        fetch(`/api/calendars?start=${start}&end=${end}`)
            .then(response => response.json())
            .then(data => {

                renderCalendarEvents(data.calendarEventDtos);
            })
            .catch(error => console.error('Error fetching calendar events:', error));
    }

    // 일정 렌더링 함수
    function renderCalendarEvents(events) {
        events.forEach(event => {
            const startDate = new Date(event.startDate);
            const day = startDate.getDate();
            const eventDayElement = document.querySelector(`.calendar-cell[data-date="${startDate.getFullYear()}-${startDate.getMonth() + 1}-${day}"]`);

            if (eventDayElement) {
                const eventLabel = document.createElement('div');
                eventLabel.className = 'event-label';
                eventLabel.textContent = event.title; // 일정 제목 표시
                eventDayElement.appendChild(eventLabel);
            }
        });
    }

    // 초기 달력 생성
    generateCalendar(currentYear, currentMonth);

    // 년/월 선택 시 달력 업데이트
    yearSelect.addEventListener('change', function() {
        generateCalendar(yearSelect.value, monthSelect.value);
    });

    monthSelect.addEventListener('change', function() {
        generateCalendar(yearSelect.value, monthSelect.value);
    });

    // 일정 추가 버튼 클릭 시 이벤트
    document.getElementById('addEventButton').addEventListener('click', function() {
        window.location.href = '/calendars/add';
    });
});
