package com.clush.test.todo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<TodoResponse> getAllTodos(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoResponse todos = todoService.getAllTodos(memberId);

        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long todoId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.getTodoById(todoId, memberId);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.addTodo(todoDto, memberId);

        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long todoId, @RequestBody TodoDto todoDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.updateTodo(todoId, todoDto, memberId);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}/status")
    public ResponseEntity<TodoDto> updateTodoStatus(@PathVariable Long todoId, @RequestBody TodoStatus status, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.updateTodoStatus(todoId, status, memberId);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<TodoDto> deleteTodo(@PathVariable Long todoId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        TodoDto todo = todoService.deleteTodo(todoId, memberId);
        return ResponseEntity.ok(todo);
    }
}
