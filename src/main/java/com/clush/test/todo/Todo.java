package com.clush.test.todo;

import com.clush.test.common.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TodoStatus.PENDING;
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

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TodoDto entityToDto() {
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .status(this.status)
                .build();
    }

}
