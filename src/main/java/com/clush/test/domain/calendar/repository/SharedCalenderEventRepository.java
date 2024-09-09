package com.clush.test.domain.calendar.repository;

import com.clush.test.domain.calendar.entity.SharedCalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SharedCalenderEventRepository extends JpaRepository<SharedCalendarEvent, Long> {
    @Query("SELECT s FROM SharedCalendarEvent s WHERE s.member.id = :memberId")
    List<SharedCalendarEvent> findByMemberId(@Param("memberId") Long memberId);
}
