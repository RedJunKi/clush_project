package com.clush.test.calendar;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class CalendarEventDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
