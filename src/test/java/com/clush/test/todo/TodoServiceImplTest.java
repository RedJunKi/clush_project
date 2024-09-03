package com.clush.test.todo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoServiceImplTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TodoService todoService;

    private Todo savedTodo;

    @BeforeEach
    void setUp() {
        savedTodo = createTodo();
    }

    @Test
    void getAllTodos() {
        //given
        TodoResponse result = todoService.getAllTodos();

        //when


        //then
        assertThat(result.getTodos().size()).isGreaterThanOrEqualTo(1);

    }

    @Test
    void getTodoById() {
        //given
        //when
        TodoDto result = todoService.getTodoById(savedTodo.getId());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(savedTodo.getTitle());
    }

    @Test
    void addTodo() {
        //given
        TodoDto todoDto = new TodoDto("생성", "테스트", TodoStatus.PENDING);

        //when
        TodoDto result = todoService.addTodo(todoDto);

        //then
        assertThat(result.getTitle()).isEqualTo(todoDto.getTitle());
        assertThat(result.getStatus()).isEqualTo(TodoStatus.PENDING);
    }

    @Test
    void updateTodo() {
        //given
        // id = 1
        Todo todo = createTodo();
        TodoDto todoDto = new TodoDto("수정본", "수정", TodoStatus.COMPLETED);

        //when
        TodoDto updateTodo = todoService.updateTodo(1L, todoDto);

        //then
        assertThat(updateTodo.getTitle()).isEqualTo(todoDto.getTitle());
        assertThat(updateTodo.getStatus()).isEqualTo(todoDto.getStatus());

        TodoDto repeatedUpdate = todoService.updateTodo(savedTodo.getId(), todoDto);
        assertThat(repeatedUpdate.getTitle()).isEqualTo(todoDto.getTitle());
        assertThat(repeatedUpdate.getStatus()).isEqualTo(todoDto.getStatus());
    }

    @Test
    void deleteTodo() {
        //given
        //when
        todoService.deleteTodo(savedTodo.getId());

        //then
        assertThatThrownBy(() -> todoService.getTodoById(savedTodo.getId())).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> todoService.deleteTodo(savedTodo.getId())).isInstanceOf(IllegalArgumentException.class);
    }

    private Todo createTodo() {
        Todo todo = new Todo("제목", "내용");
        em.persist(todo);
        em.flush();
        return todo;
    }
}