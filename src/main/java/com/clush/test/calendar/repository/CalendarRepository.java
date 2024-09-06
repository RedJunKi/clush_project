package com.clush.test.calendar.repository;

import com.clush.test.calendar.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEvent, Long>, CalendarRepositoryCustom {
    Optional<CalendarEvent> findByIdAndMemberId(Long eventId, Long memberId);
}
