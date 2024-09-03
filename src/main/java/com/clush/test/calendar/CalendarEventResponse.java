package com.clush.test.calendar;

import lombok.Getter;

import java.util.List;

@Getter
public class CalendarEventResponse {
    private List<CalendarEventDto> calendarEventDtos;

    public CalendarEventResponse(List<CalendarEventDto> calendarEventDtos) {
        this.calendarEventDtos = calendarEventDtos;
    }
}
