package com.clush.test.calendar;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.clush.test.calendar.QCalendarEvent.calendarEvent;

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
