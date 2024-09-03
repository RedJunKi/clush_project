package com.clush.test.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEvent, Long>, CalendarRepositoryCustom {
}
