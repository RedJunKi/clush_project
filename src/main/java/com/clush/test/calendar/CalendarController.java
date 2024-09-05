package com.clush.test.calendar;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
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
    public ResponseEntity<CalendarEventDto> getEventById(@PathVariable Long eventId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");


        CalendarEventDto event = calendarService.getEventById(eventId, memberId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<CalendarEventDto> addEvent(@RequestBody CalendarEventDto eventDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.addEvent(eventDto, memberId);

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> updateEvent(@PathVariable Long eventId, @RequestBody CalendarEventDto eventDto, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.updateEvent(eventId, eventDto, memberId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> deleteEvent(@PathVariable Long eventId, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        CalendarEventDto event = calendarService.deleteEvent(eventId, memberId);
        return ResponseEntity.ok(event);
    }
}
