package com.clush.test.todo;

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
