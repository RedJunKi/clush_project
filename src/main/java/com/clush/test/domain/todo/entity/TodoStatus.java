package com.clush.test.domain.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TodoStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    DONE
}
