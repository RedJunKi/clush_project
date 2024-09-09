package com.clush.test.domain.calendar.repository;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.clush.test.domain.calendar.entity.QCalendarEvent.calendarEvent;


@Repository
@RequiredArgsConstructor
public class CalendarRepositoryCustomImpl implements CalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CalendarEvent> findAllByStartDateBetweenAndMemberId(LocalDateTime startDate, LocalDateTime endDate, Long memberId) {
        return queryFactory.selectFrom(calendarEvent)
                .where(calendarEvent.startDate.between(startDate, endDate))
                .where(calendarEvent.member.id.eq(memberId))
                .fetch();
    }
}
