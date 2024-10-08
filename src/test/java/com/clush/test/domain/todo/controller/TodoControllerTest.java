package com.clush.test.domain.todo.controller;

import com.clush.test.domain.todo.entity.TodoDto;
import com.clush.test.domain.todo.entity.TodoResponse;
import com.clush.test.domain.todo.entity.TodoStatus;
import com.clush.test.domain.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testMemberId = 1L;
    private TodoDto testTodoDto;
    private TodoResponse testTodoResponse;

    @BeforeEach
    void setUp() {
        testTodoDto = new TodoDto("Test Todo", "Test Description", TodoStatus.PENDING);
        testTodoResponse = new TodoResponse(List.of(testTodoDto));
    }

    @Test
    void getAllTodos() throws Exception {
        // given
        when(todoService.getAllTodos(anyLong())).thenReturn(testTodoResponse);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.todos[0].title").value(testTodoDto.getTitle()));
    }

    @Test
    void getTodoById() throws Exception {
        // given
        when(todoService.getTodoById(anyLong(), anyLong())).thenReturn(testTodoDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todos/{todoId}", 1L)
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testTodoDto.getTitle()));
    }

    @Test
    void addTodo() throws Exception {
        // given
        when(todoService.addTodo(any(), anyLong())).thenReturn(testTodoDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTodoDto))
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testTodoDto.getTitle()));
    }

    @Test
    void updateTodo() throws Exception {
        // given
        when(todoService.updateTodo(anyLong(), any(), anyLong())).thenReturn(testTodoDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/{todoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTodoDto))
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testTodoDto.getTitle()));
    }

    @Test
    void updateTodoStatus() throws Exception {
        // given
        TodoDto testTodoDto = new TodoDto();
        testTodoDto.setStatus(TodoStatus.COMPLETED);
        when(todoService.updateTodoStatus(anyLong(), any(), anyLong())).thenReturn(testTodoDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/{todoId}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + TodoStatus.COMPLETED.name() + "\"")
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(TodoStatus.COMPLETED.name())); // 기대하는 값으로 검증
    }

    @Test
    void deleteTodo() throws Exception {
        // given
        when(todoService.deleteTodo(anyLong(), anyLong())).thenReturn(testTodoDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/{todoId}", 1L)
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testTodoDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testTodoDto.getDescription()));
    }
}