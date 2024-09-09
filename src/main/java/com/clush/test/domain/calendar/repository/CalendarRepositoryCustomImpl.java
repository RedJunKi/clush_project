package com.clush.test.domain.calendar.repository;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.clush.test.domain.calendar.entity.QCalendarEvent.calendarEvent;
import static com.querydsl.core.types.dsl.Expressions.dateTemplate;


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

    @Override
    public Optional<List<CalendarEvent>> findByEndDate(LocalDate endDate) {
        DateTemplate<LocalDate> dateTemplate = dateTemplate(LocalDate.class, "DATE({0})", calendarEvent.endDate);

        return Optional.ofNullable(queryFactory.selectFrom(calendarEvent)
                .where(dateTemplate.eq(endDate))
                .fetch());
    }


}
