package com.clush.test.domain.todo.controller;

import com.clush.test.domain.todo.entity.TodoDto;
import com.clush.test.domain.todo.entity.TodoResponse;
import com.clush.test.domain.todo.service.TodoService;
import com.clush.test.domain.todo.entity.TodoStatus;
import com.clush.test.global.annotation.CheckSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Todo", description = "Todo API")
@RequestMapping("/api/todos")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "모든 Todo 조회", description = "세션에 저장된 memberId로 회원이 작성한 모든 Todo 가져오기")
    @CheckSession
    public ResponseEntity<TodoResponse> getAllTodos(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoResponse todos = todoService.getAllTodos(memberId);

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "Todo 단건 조회", description = "대상 Todo id와 세션에 저장된 memberId로 작성한 Todo 단건 가져오기")
    @CheckSession
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long todoId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.getTodoById(todoId, memberId);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    @Operation(summary = "Todo 저장 (status = PENDING, IN_PROGRESS, COMPLETED, DONE)", description = "Todo 저장하기 (status = PENDING, IN_PROGRESS, COMPLETED, DONE)")
    @CheckSession
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.addTodo(todoDto, memberId);

        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "Todo 수정", description = "대상 Todo id와 세션에 저장된 memberId로 작성한 Todo 수정하기")
    @CheckSession
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long todoId, @RequestBody TodoDto todoDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.updateTodo(todoId, todoDto, memberId);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}/status")
    @Operation(summary = "Todo 상태 수정", description = "대상 Todo id와 세션에 저장된 memberId로 작성한 Todo 상태만 수정하기")
    @CheckSession
    public ResponseEntity<TodoDto> updateTodoStatus(@PathVariable Long todoId, @RequestBody TodoStatus status, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.updateTodoStatus(todoId, status, memberId);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "Todo 삭제", description = "대상 Todo id와 세션에 저장된 memberId로 작성한 Todo 삭제하기")
    @CheckSession
    public ResponseEntity<TodoDto> deleteTodo(@PathVariable Long todoId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.deleteTodo(todoId, memberId);
        return ResponseEntity.ok(todo);
    }
}

