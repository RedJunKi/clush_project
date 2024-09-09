package com.clush.test.domain.calendar.repository;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEvent, Long>, CalendarRepositoryCustom {
    Optional<CalendarEvent> findByIdAndMemberId(Long eventId, Long memberId);
}
