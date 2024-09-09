package com.clush.test.domain.todo.entity;

import java.util.List;

public class TodoResponse {

    private List<TodoDto> todos;

    public TodoResponse(List<TodoDto> todos) {
        this.todos = todos;
    }

    public List<TodoDto> getTodos() {
        return todos;
    }
}
