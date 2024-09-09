package com.clush.test.domain.todo.entity;

import com.clush.test.domain.common.BaseEntity;
import com.clush.test.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    public Todo(String title, String description, Member member) {
        this.title = title;
        this.description = description;
        setMember(member);
        this.status = TodoStatus.PENDING;
    }

    public void setMember(Member member) {

        if (this.member != null) {
            this.member.removeTodo(this);
        }
        this.member = member;
        member.addTodo(this);
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

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public TodoStatus getStatus() {
        return status;
    }
}
