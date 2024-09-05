package com.clush.test.calendar;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarRepositoryCustom {
    List<CalendarEvent> findAllByStartDateBetweenAndMemberId(LocalDateTime startDate, LocalDateTime endDate, Long memberId);
}
