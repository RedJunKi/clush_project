package com.clush.test.domain.calendar.service;

import com.clush.test.domain.calendar.entity.CalendarEventDto;
import com.clush.test.domain.calendar.entity.CalendarEventResponse;

import java.time.LocalDateTime;

public interface CalendarService {
    CalendarEventResponse getAllEventsBetween(LocalDateTime start, LocalDateTime end, Long memberId);

    CalendarEventDto getEventById(Long eventId, Long memberId);

    CalendarEventDto addEvent(CalendarEventDto eventDto, Long memberId);

    CalendarEventDto updateEvent(Long eventId, CalendarEventDto eventDto, Long memberId);

    CalendarEventDto deleteEvent(Long eventId, Long memberId);
}
