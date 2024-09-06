package com.clush.test.todo.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class TodoDto {

    private long id;
    private String title;
    private String description;
    private TodoStatus status;

    public TodoDto(long id, String title, String description, TodoStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TodoDto(String title, String description, TodoStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TodoDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TodoStatus getStatus() {
        return status;
    }


    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}
