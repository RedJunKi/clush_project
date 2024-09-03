package com.clush.test.todo;

public interface TodoService {

    TodoResponse getAllTodos();

    TodoResponse getTodoById(int todoId);

    TodoResponse addTodo(TodoDto todoDto);

    TodoResponse updateTodo(int todoId, TodoDto todoDto);

    TodoResponse deleteTodo(int todoId);
}
