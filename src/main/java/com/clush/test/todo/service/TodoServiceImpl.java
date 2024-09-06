package com.clush.test.todo.service;

import com.clush.test.global.BusinessLogicException;
import com.clush.test.global.ExceptionCode;
import com.clush.test.member.entity.Member;
import com.clush.test.member.repository.MemberRepository;
import com.clush.test.todo.repository.TodoRepository;
import com.clush.test.todo.entity.TodoStatus;
import com.clush.test.todo.entity.Todo;
import com.clush.test.todo.entity.TodoDto;
import com.clush.test.todo.entity.TodoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Override
    public TodoResponse getAllTodos(Long memberId) {
        List<Todo> todos = todoRepository.findAllByMemberId(memberId);

        List<TodoDto> todoDtos = todos.stream()
                .map(Todo::entityToDto)
                .toList();

        return new TodoResponse(todoDtos);
    }

    @Override
    public TodoDto getTodoById(Long todoId, Long memberId) {
        Todo result = todoRepository.findByIdAndMemberId(todoId, memberId);

        return result.entityToDto();
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Todo todo = new Todo(todoDto.getTitle(), todoDto.getDescription(), member);
        Todo result = todoRepository.save(todo);

        return result.entityToDto();
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todoDto, Long memberId) {
        Todo todo = todoRepository.findByIdAndMemberId(todoId, memberId);

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());

        if (todoDto.getStatus() != null) {
            todo.setStatus(todoDto.getStatus());
        }

        todoRepository.save(todo);

        return todo.entityToDto();
    }

    @Override
    public TodoDto updateTodoStatus(Long todoId, TodoStatus status, Long memberId) {
        Todo todo = todoRepository.findByIdAndMemberId(todoId, memberId);

        todo.setStatus(status);

        todoRepository.save(todo);
        return todo.entityToDto();
    }

    @Override
    public TodoDto deleteTodo(Long todoId, Long memberId) {
        Todo todo = todoRepository.findByIdAndMemberId(todoId, memberId);

        todoRepository.delete(todo);
        return todo.entityToDto();
    }
}
