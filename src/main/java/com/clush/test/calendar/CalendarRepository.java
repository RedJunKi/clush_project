package com.clush.test.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEvent, Long>, CalendarRepositoryCustom {
    Optional<CalendarEvent> findByIdAndMemberId(Long eventId, Long memberId);
}
