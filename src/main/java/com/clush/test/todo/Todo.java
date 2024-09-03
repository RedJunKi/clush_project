package com.clush.test.todo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Todo {

    @Column(name = "ITEM_ID")
    private Long id;

    private String title;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean cancelYn;



}
