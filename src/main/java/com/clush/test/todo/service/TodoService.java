package com.clush.test.todo.service;

import com.clush.test.todo.entity.TodoStatus;
import com.clush.test.todo.entity.TodoDto;
import com.clush.test.todo.entity.TodoResponse;

public interface TodoService {

    TodoResponse getAllTodos(Long memberId);

    TodoDto getTodoById(Long todoId, Long memberId);

    TodoDto addTodo(TodoDto todoDto, Long memberId);

    TodoDto updateTodo(Long todoId, TodoDto todoDto, Long memberId);

    TodoDto deleteTodo(Long todoId, Long memberId);

    TodoDto updateTodoStatus(Long todoId, TodoStatus status, Long memberId);
}
