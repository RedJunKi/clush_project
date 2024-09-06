package com.clush.test.calendar.controller;

import com.clush.test.calendar.entity.CalendarEventDto;
import com.clush.test.calendar.entity.CalendarEventResponse;
import com.clush.test.calendar.service.CalendarService;
import com.clush.test.calendar.util.CalendarUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Tag(name = "CalendarEvent", description = "일정 API")
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    @Operation(summary = "일정 다건 조회", description = "회원이 저장한 일정 조회하기(시작일, 종료일 미입력시 해당 월 조회)")
    public ResponseEntity<CalendarEventResponse> getAllEvents(@RequestParam(value = "start", required = false) LocalDateTime start,
                                                              @RequestParam(value = "end", required = false) LocalDateTime end,
                                                              HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        if (start == null) {
            start = CalendarUtil.getFirstDayOfMonth();
        }

        if (end == null) {
            end = CalendarUtil.getLastDayOfMonth();
        }

        CalendarEventResponse events = calendarService.getAllEventsBetween(start, end, memberId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "일정 단건 조회", description = "회원이 저장한 일정 단건 조회하기")
    public ResponseEntity<CalendarEventDto> getEventById(@PathVariable Long eventId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");


        CalendarEventDto event = calendarService.getEventById(eventId, memberId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    @Operation(summary = "일정 추가", description = "일정 추가하기")
    public ResponseEntity<CalendarEventDto> addEvent(@RequestBody CalendarEventDto eventDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.addEvent(eventDto, memberId);

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "일정 수정", description = "일정 ID로 일정 수정하기")
    public ResponseEntity<CalendarEventDto> updateEvent(@PathVariable Long eventId, @RequestBody CalendarEventDto eventDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.updateEvent(eventId, eventDto, memberId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "일정 삭제", description = "일정 ID로 저장된 일정 삭제하기")
    public ResponseEntity<CalendarEventDto> deleteEvent(@PathVariable Long eventId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.deleteEvent(eventId, memberId);
        return ResponseEntity.ok(event);
    }
}

