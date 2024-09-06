package com.clush.test.calendar.entity;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
