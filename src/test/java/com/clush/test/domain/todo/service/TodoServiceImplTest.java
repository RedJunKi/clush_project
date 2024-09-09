package com.clush.test.domain.todo.service;

import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.repository.MemberRepository;

import com.clush.test.domain.todo.entity.Todo;
import com.clush.test.domain.todo.entity.TodoDto;
import com.clush.test.domain.todo.entity.TodoResponse;
import com.clush.test.domain.todo.entity.TodoStatus;
import com.clush.test.domain.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoServiceImplTest {

    @Autowired
    private TodoServiceImpl todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private Todo testTodo;

    @BeforeEach
    void setUp() {
        testMember = new Member();
        testMember.setEmail("testuser@example.com");
        testMember.setUsername("TestUser");
        testMember.setPassword("password");

        memberRepository.save(testMember);

        testTodo = new Todo();
        testTodo.setTitle("Initial Title");
        testTodo.setDescription("Initial Description");
        testTodo.setMember(testMember);

        todoRepository.save(testTodo);
    }

    @Test
    void testGetAllTodos() {
        // when
        TodoResponse result = todoService.getAllTodos(testMember.getId());

        // then
        assertThat(result.getTodos()).isNotEmpty();
        assertThat(result.getTodos().get(0).getTitle()).isEqualTo(testTodo.getTitle());
    }

    @Test
    void testGetTodoById() {
        // when
        TodoDto result = todoService.getTodoById(testTodo.getId(), testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo(testTodo.getTitle());
        assertThat(result.getDescription()).isEqualTo(testTodo.getDescription());
    }

    @Test
    void testAddTodo() {
        // given
        TodoDto newTodo = new TodoDto("New Title", "New Description");

        // when
        TodoDto result = todoService.addTodo(newTodo, testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getDescription()).isEqualTo("New Description");

        List<Todo> todos = todoRepository.findAllByMemberId(testMember.getId());
        assertThat(todos).hasSize(2); // 기존 1개 + 새로 추가된 1개
    }

    @Test
    void testUpdateTodo() {
        // given
        TodoDto updatedTodo = new TodoDto("Updated Title", "Updated Description");

        // when
        TodoDto result = todoService.updateTodo(testTodo.getId(), updatedTodo, testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getDescription()).isEqualTo("Updated Description");

        Todo updatedTodoFromDb = todoRepository.findByIdAndMemberId(testTodo.getId(), testMember.getId());
        assertThat(updatedTodoFromDb.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void testUpdateTodoStatus() {
        // given
        TodoStatus newStatus = TodoStatus.COMPLETED;

        // when
        TodoDto result = todoService.updateTodoStatus(testTodo.getId(), newStatus, testMember.getId());

        // then
        assertThat(result.getStatus()).isEqualTo(newStatus);

        Todo updatedTodoFromDb = todoRepository.findByIdAndMemberId(testTodo.getId(), testMember.getId());
        assertThat(updatedTodoFromDb.getStatus()).isEqualTo(newStatus);
    }

    @Test
    void testDeleteTodo() {
        // when
        TodoDto result = todoService.deleteTodo(testTodo.getId(), testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo(testTodo.getTitle());

        Optional<Todo> deletedTodo = todoRepository.findById(testTodo.getId());
        assertThat(deletedTodo).isEmpty();
    }
}