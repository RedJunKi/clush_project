package com.clush.test.domain.calendar.controller;

import com.clush.test.domain.calendar.controller.CalendarController;

import com.clush.test.domain.calendar.entity.CalendarEventDto;
import com.clush.test.domain.calendar.entity.CalendarEventResponse;
import com.clush.test.domain.calendar.service.CalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CalendarController.class)
@AutoConfigureMockMvc
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalendarService calendarService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testMemberId = 1L;
    private CalendarEventDto testEventDto;
    private CalendarEventResponse testEventResponse;

    @BeforeEach
    void setUp() {
        testEventDto = new CalendarEventDto("Test Event", "Test Description",
                LocalDateTime.of(2024, 9, 1, 10, 0),
                LocalDateTime.of(2024, 9, 2, 10, 0));
        testEventResponse = new CalendarEventResponse(List.of(testEventDto));
    }

    @Test
    void getAllEvents() throws Exception {
        // given
        when(calendarService.getAllEventsBetween(any(), any(), anyLong())).thenReturn(testEventResponse);

        // when & then
        mockMvc.perform(get("/api/calendars")
                        .param("start", "2024-09-01T00:00:00")
                        .param("end", "2024-09-30T23:59:59")
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.calendarEventDtos[0].title").value(testEventDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.calendarEventDtos[0].description").value(testEventDto.getDescription()));
    }

    @Test
    void getEventById() throws Exception {
        // given
        when(calendarService.getEventById(anyLong(), anyLong())).thenReturn(testEventDto);

        // when & then
        mockMvc.perform(get("/api/calendars/{eventId}", 1L)
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testEventDto.getDescription()));
    }

    @Test
    void addEvent() throws Exception {
        // given
        when(calendarService.addEvent(any(), anyLong())).thenReturn(testEventDto);

        // when & then
        mockMvc.perform(post("/api/calendars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEventDto))
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testEventDto.getDescription()));
    }

    @Test
    void updateEvent() throws Exception {
        // given
        when(calendarService.updateEvent(anyLong(), any(), anyLong())).thenReturn(testEventDto);

        // when & then
        mockMvc.perform(put("/api/calendars/{eventId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEventDto))
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testEventDto.getDescription()));
    }

    @Test
    void deleteEvent() throws Exception {
        // given
        when(calendarService.deleteEvent(anyLong(), anyLong())).thenReturn(testEventDto);

        // when & then
        mockMvc.perform(delete("/api/calendars/{eventId}", 1L)
                        .sessionAttr("memberId", testMemberId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(testEventDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(testEventDto.getDescription()));
    }
}