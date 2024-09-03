package com.clush.test.calendar;

import java.time.LocalDateTime;
import java.util.Date;

public interface CalendarService {
    CalendarEventResponse getAllEventsBetween(LocalDateTime start, LocalDateTime end);

    CalendarEventDto getEventById(long eventId);

    CalendarEventDto addEvent(CalendarEventDto eventDto);

    CalendarEventDto updateEvent(long eventId, CalendarEventDto eventDto);

    CalendarEventDto deleteEvent(long eventId);
}
