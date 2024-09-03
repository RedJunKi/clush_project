package com.clush.test.todo;

public interface TodoService {

    TodoResponse getAllTodos();

    TodoDto getTodoById(long todoId);

    TodoDto addTodo(TodoDto todoDto);

    TodoDto updateTodo(long todoId, TodoDto todoDto);

    TodoDto deleteTodo(long todoId);
}
