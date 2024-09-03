package com.clush.test.todo;

import lombok.Builder;

@Builder
public class TodoDto {

    private String title;
    private String description;
    private TodoStatus status;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}
