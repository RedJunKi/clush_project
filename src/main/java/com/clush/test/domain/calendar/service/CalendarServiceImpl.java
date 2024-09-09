package com.clush.test.domain.calendar.service;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.clush.test.domain.calendar.entity.CalendarEventDto;
import com.clush.test.domain.calendar.entity.CalendarEventResponse;
import com.clush.test.domain.calendar.repository.CalendarRepository;
import com.clush.test.global.exception.BusinessLogicException;
import com.clush.test.global.exception.ExceptionCode;
import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberRepository memberRepository;

    @Override
    public CalendarEventResponse getAllEventsBetween(LocalDateTime start, LocalDateTime end, Long memberId) {
        List<CalendarEvent> calendarEvents = calendarRepository.findAllByStartDateBetweenAndMemberId(start, end, memberId);

        List<CalendarEventDto> calendarEventDtos = calendarEvents.stream()
                .map(CalendarEvent::entityToDto)
                .toList();

        return new CalendarEventResponse(calendarEventDtos);
    }

    @Override
    public CalendarEventDto getEventById(Long eventId, Long memberId) {
        CalendarEvent result = findById(eventId, memberId);

        return result.entityToDto();
    }

    @Override
    public CalendarEventDto addEvent(CalendarEventDto eventDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        CalendarEvent calendarEvent = new CalendarEvent();

        makeEventEntity(eventDto, calendarEvent);
        calendarEvent.setMember(member);

        CalendarEvent result = calendarRepository.save(calendarEvent);
        return result.entityToDto();
    }

    @Override
    public CalendarEventDto updateEvent(Long eventId, CalendarEventDto eventDto, Long memberId) {
        CalendarEvent calendarEvent = findById(eventId, memberId);

        makeEventEntity(eventDto, calendarEvent);

        CalendarEvent result = calendarRepository.save(calendarEvent);
        return result.entityToDto();
    }

    @Override
    public CalendarEventDto deleteEvent(Long eventId, Long memberId) {
        CalendarEvent findOne = findById(eventId, memberId);
        calendarRepository.delete(findOne);

        return findOne.entityToDto();
    }

    private CalendarEvent findById(Long eventId, Long memberId) {
        return calendarRepository.findByIdAndMemberId(eventId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다."));
    }

    private static void makeEventEntity(CalendarEventDto eventDto, CalendarEvent calendarEvent) {
        calendarEvent.setTitle(eventDto.getTitle());
        calendarEvent.setDescription(eventDto.getDescription());
        calendarEvent.setStartDate(eventDto.getStartDate());
        calendarEvent.setEndDate(eventDto.getEndDate());
    }
}
