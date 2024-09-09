package com.clush.test.domain.member.entity;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.clush.test.domain.common.BaseEntity;
import com.clush.test.domain.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;
    private String email;
    private String password;
    private String username;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalendarEvent> calendarEvents = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();


    public Member(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Member(long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public MemberPostDto entityToDto() {
        return new MemberPostDto(this.id, this.email, this.username);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    public void removeTodo(Todo todo) {
        this.todos.remove(todo);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvents.add(calendarEvent);
    }

    public void removeCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvents.remove(calendarEvent);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
