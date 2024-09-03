package com.clush.test.todo;

import com.clush.test.common.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    private boolean cancelYn;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TodoStatus.PENDING;
        this.cancelYn = false;
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

    public TodoDto entityToDto() {
        return TodoDto.builder()
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
