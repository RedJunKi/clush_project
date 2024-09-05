package com.clush.test.calendar;

import java.time.LocalDateTime;
import java.util.Date;

public interface CalendarService {
    CalendarEventResponse getAllEventsBetween(LocalDateTime start, LocalDateTime end, Long memberId);

    CalendarEventDto getEventById(Long eventId, Long memberId);

    CalendarEventDto addEvent(CalendarEventDto eventDto, Long memberId);

    CalendarEventDto updateEvent(Long eventId, CalendarEventDto eventDto, Long memberId);

    CalendarEventDto deleteEvent(Long eventId, Long memberId);
}
