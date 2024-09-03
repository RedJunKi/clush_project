package com.clush.test.todo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
    private boolean cancelYn;



}
