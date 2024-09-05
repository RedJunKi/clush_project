document.addEventListener('DOMContentLoaded', function() {
     const token = localStorage.getItem('authToken'); // JWT 토큰을 로컬 스토리지에서 가져온다
        if (token) {
            const memberId = getMemberIdFromToken(token); // JWT 토큰에서 memberId를 추출하는 함수
            fetchTodos(memberId); // memberId를 사용하여 TODO를 가져온다
        }
});

 const token = localStorage.getItem('authToken'); // JWT 토큰을 로컬 스토리지에서 가져온다
    if (token) {
        const memberId = getMemberIdFromToken(token); // JWT 토큰에서 memberId를 추출하는 함수
        fetchTodos(memberId); // memberId를 사용하여 TODO를 가져온다
    }

function fetchTodos() {
    fetch('/api/todos?memberId=${memberId}')
        .then(response => response.json())
        .then(data => {
            renderTodos(data.todos);
        })
        .catch(error => console.error('Error fetching TODOs:', error));
}

function renderTodos(todos) {
    const todoColumn = document.getElementById('todoColumn');
    const doingColumn = document.getElementById('doingColumn');
    const doneColumn = document.getElementById('doneColumn');

    todos.forEach(todo => {
        const todoCard = createTodoCard(todo);

        if (todo.status === 'PENDING') {
            todoColumn.appendChild(todoCard);
        } else if (todo.status === 'IN_PROGRESS') {
            doingColumn.appendChild(todoCard);
        } else if (todo.status === 'COMPLETED') {
            doneColumn.appendChild(todoCard);
        }
    });
}

function createTodoCard(todo) {
    const card = document.createElement('div');
    card.className = 'card';
    card.dataset.id = todo.id;

    const title = document.createElement('h3');
    title.textContent = todo.title;

    const description = document.createElement('p');
    description.textContent = todo.description;

    card.appendChild(title);
    card.appendChild(description);

    if (todo.status !== 'COMPLETED') {
        const nextButton = document.createElement('button');
        nextButton.className = 'arrow right';
        nextButton.textContent = '→';

        nextButton.addEventListener('click', function() {
            moveCard(todo, getNextStatus(todo.status));
        });

        card.appendChild(nextButton);
    }

    if (todo.status !== 'PENDING') {
        const beforeButton = document.createElement('button');
        beforeButton.className = 'arrow left';
        beforeButton.textContent = '←';

        beforeButton.addEventListener('click', function() {
            moveCard(todo, getBeforeStatus(todo.status));
        });

        card.appendChild(beforeButton);
    }
    if (todo.status === 'COMPLETED') {
        const completeButton = document.createElement('button');
        completeButton.textContent = '완료';
        completeButton.className = 'complete';

        completeButton.addEventListener('click', function() {
            markAsDone(todo);
        });

        card.appendChild(completeButton);
    }
    return card;
}

function markAsDone(todo) {
    fetch(`/api/todos/${todo.id}/status`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify('DONE')
    })
    .then(response => response.json())
    .then(updatedTodo => {
        document.querySelector(`.card[data-id='${todo.id}']`).remove();
        renderTodos([updatedTodo]);
    })
    .catch(error => console.error('Error marking TODO as DONE:', error));
}

function getNextStatus(currentStatus) {
    if (currentStatus === 'PENDING') {
        return 'IN_PROGRESS';
    } else if( currentStatus === 'IN_PROGRESS') {
        return 'COMPLETED';
    }
    return null;
}

function getBeforeStatus(currentStatus) {
    if (currentStatus === 'IN_PROGRESS') {
        return 'PENDING';
    } else if( currentStatus === 'COMPLETED') {
        return 'IN_PROGRESS';
    }
    return null;
}

function moveCard(todo, nextStatus) {
    if (!nextStatus) return;

    fetch(`/api/todos/${todo.id}/status`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nextStatus)
    })
    .then(response => response.json())
    .then(updatedTodo => {
        document.querySelector(`.card[data-id='${todo.id}']`).remove();
        renderTodos([updatedTodo]);
    })
    .catch(error => console.error('Error updating TODO:', error));
}
