package com.clush.test.todo;

import com.clush.test.global.BusinessLogicException;
import com.clush.test.global.ExceptionCode;
import com.clush.test.member.Member;
import com.clush.test.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TodoResponse getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        List<TodoDto> todoDtos = todos.stream()
                .map(Todo::entityToDto)
                .toList();

        return new TodoResponse(todoDtos);
    }

    @Override
    public TodoDto getTodoById(long todoId) {
        Todo result = findById(todoId);

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
    public TodoDto updateTodo(long todoId, TodoDto todoDto) {
        Todo todo = findById(todoId);
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setStatus(todoDto.getStatus());

        todoRepository.save(todo);

        return todo.entityToDto();
    }

    @Override
    public TodoDto updateTodoStatus(long todoId, TodoStatus status) {
        Todo todo = findById(todoId);
        todo.setStatus(status);

        todoRepository.save(todo);
        return todo.entityToDto();
    }

    @Override
    public TodoDto deleteTodo(long todoId) {
        Todo todo = findById(todoId);
        todoRepository.delete(todo);
        return todo.entityToDto();
    }

    private Todo findById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("찾을수 없는 아이디입니다."));
    }
}
