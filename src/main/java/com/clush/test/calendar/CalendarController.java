package com.clush.test.calendar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public ResponseEntity<CalendarEventResponse> getAllEvents(@RequestParam(value = "start", required = false) LocalDateTime start,
                                                              @RequestParam(value = "end", required = false) LocalDateTime end) {
        if (start == null) {
            start = CalendarUtil.getFirstDayOfMonth();
        }

        if (end == null) {
            end = CalendarUtil.getLastDayOfMonth();
        }

        CalendarEventResponse events = calendarService.getAllEventsBetween(start, end);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> getEventById(@PathVariable long eventId) {
        CalendarEventDto event = calendarService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<CalendarEventDto> addEvent(@RequestBody CalendarEventDto eventDto) {
        CalendarEventDto event = calendarService.addEvent(eventDto);

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> updateEvent(@PathVariable long eventId, @RequestBody CalendarEventDto eventDto) {
        CalendarEventDto event = calendarService.updateEvent(eventId, eventDto);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<CalendarEventDto> deleteEvent(@PathVariable long eventId) {
        CalendarEventDto event = calendarService.deleteEvent(eventId);
        return ResponseEntity.ok(event);
    }
}
