package com.clush.test.domain.calendar.repository;

import com.clush.test.domain.calendar.entity.CalendarEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepositoryCustom {
    List<CalendarEvent> findAllByStartDateBetweenAndMemberId(LocalDateTime startDate, LocalDateTime endDate, Long memberId);
}
