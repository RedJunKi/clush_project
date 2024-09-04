package com.clush.test.todo;

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
    public ResponseEntity<TodoResponse> getAllTodos() {
        TodoResponse todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable long todoId) {
        TodoDto todo = todoService.getTodoById(todoId);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        TodoDto todo = todoService.addTodo(todoDto);

        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable long todoId, @RequestBody TodoDto todoDto) {
        TodoDto todo = todoService.updateTodo(todoId, todoDto);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}/status")
    public ResponseEntity<TodoDto> updateTodoStatus(@PathVariable long todoId, @RequestBody TodoStatus status) {
        TodoDto todo = todoService.updateTodoStatus(todoId, status);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<TodoDto> deleteTodo(@PathVariable long todoId) {
        TodoDto todo = todoService.deleteTodo(todoId);
        return ResponseEntity.ok(todo);
    }
}
