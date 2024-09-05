package com.clush.test.calendar;

import com.clush.test.todo.TodoDto;
import com.clush.test.todo.TodoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CalendarServiceImplTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CalendarService calendarService;

    private CalendarEvent savedCalendarEvent;

//    @BeforeEach
//    void setUp() {
//        savedCalendarEvent = createCalendarEvent();
//    }
//
//    @Test
//    void getAllEventsBetween() {
//        //given
//        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
//        LocalDateTime endDate = LocalDateTime.of(2024, 9, 30, 0, 0, 0);
//
//        //when
//        CalendarEventResponse calendarEvents = calendarService.getAllEventsBetween(startDate, endDate);
//
//        //then
//        assertThat(calendarEvents.getCalendarEventDtos().size()).isGreaterThanOrEqualTo(1);
//    }
//
//    @Test
//    void getEventById() {
//        //given
//        //when
//        CalendarEventDto result = calendarService.getEventById(savedCalendarEvent.getId());
//
//        //then
//        assertThat(result).isNotNull();
//        assertThat(result.getTitle()).isEqualTo(savedCalendarEvent.getTitle());
//    }
//
//    @Test
//    void addEvent() {
//        //given
//        CalendarEvent calendarEvent = new CalendarEvent("생성", "테스트", LocalDateTime.of(2024, 9, 3, 0, 0, 0), LocalDateTime.of(2024, 9, 10, 23, 59, 59));
//
//        //when
//        CalendarEventDto result = calendarService.addEvent(calendarEvent.entityToDto());
//
//        //then
//        assertThat(result.getTitle()).isEqualTo(calendarEvent.getTitle());
//        assertThat(result.getEndDate()).isEqualTo(calendarEvent.getEndDate());
//    }
//
//    @Test
//    void updateEvent() {
//        //given
//        CalendarEventDto calendarEventDto = new CalendarEventDto("수정본", "수정내용", LocalDateTime.now(), LocalDateTime.now());
//
//        //when
//        calendarService.updateEvent(savedCalendarEvent.getId(), calendarEventDto);
//        CalendarEventDto result = calendarService.getEventById(savedCalendarEvent.getId());
//
//        //then
//        assertThat(result.getTitle()).isEqualTo(calendarEventDto.getTitle());
//        assertThat(result.getStartDate()).isEqualTo(calendarEventDto.getStartDate());
//
//        CalendarEventDto repeatedUpdate = calendarService.updateEvent(savedCalendarEvent.getId(), calendarEventDto);
//        assertThat(repeatedUpdate.getTitle()).isEqualTo(calendarEventDto.getTitle());
//        assertThat(repeatedUpdate.getDescription()).isEqualTo(calendarEventDto.getDescription());
//    }
//
//    @Test
//    void deleteEvent() {
//        //given
//        //when
//        calendarService.deleteEvent(savedCalendarEvent.getId());
//
//        //then
//        assertThatThrownBy(() -> calendarService.getEventById(savedCalendarEvent.getId()));
//        assertThatThrownBy(() -> calendarService.deleteEvent(savedCalendarEvent.getId()));
//    }
//
//    private CalendarEvent createCalendarEvent() {
//        CalendarEvent calendarEvent = new CalendarEvent("제목", "내용", LocalDateTime.of(2024, 9, 3, 0, 0, 0), LocalDateTime.of(2024, 9, 10, 23, 59, 59));
//        em.persist(calendarEvent);
//        em.flush();
//        return calendarEvent;
//    }
}