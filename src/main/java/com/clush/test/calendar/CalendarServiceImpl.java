package com.clush.test.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private final CalendarRepository calendarRepository;

    @Override
    public CalendarEventResponse getAllEventsBetween(LocalDateTime start, LocalDateTime end) {
        List<CalendarEvent> calendarEvents = calendarRepository.findAllByStartDateBetween(start, end);

        List<CalendarEventDto> calendarEventDtos = calendarEvents.stream()
                .map(CalendarEvent::entityToDto)
                .toList();

        return new CalendarEventResponse(calendarEventDtos);
    }

    @Override
    public CalendarEventDto getEventById(long eventId) {
        CalendarEvent result = findById(eventId);

        return result.entityToDto();
    }

    @Override
    public CalendarEventDto addEvent(CalendarEventDto eventDto) {
        CalendarEvent calendarEvent = new CalendarEvent();

        makeEventEntity(eventDto, calendarEvent);

        CalendarEvent result = calendarRepository.save(calendarEvent);
        return result.entityToDto();
    }

    @Override
    public CalendarEventDto updateEvent(long eventId, CalendarEventDto eventDto) {
        CalendarEvent calendarEvent = findById(eventId);

        makeEventEntity(eventDto, calendarEvent);

        CalendarEvent result = calendarRepository.save(calendarEvent);
        return result.entityToDto();
    }

    @Override
    public CalendarEventDto deleteEvent(long eventId) {
        CalendarEvent findOne = findById(eventId);
        calendarRepository.delete(findOne);

        return findOne.entityToDto();
    }

    private CalendarEvent findById(long eventId) {
        return calendarRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다."));
    }

    private static void makeEventEntity(CalendarEventDto eventDto, CalendarEvent calendarEvent) {
        calendarEvent.setTitle(eventDto.getTitle());
        calendarEvent.setDescription(eventDto.getDescription());
        calendarEvent.setStartDate(eventDto.getStartDate());
        calendarEvent.setEndDate(eventDto.getEndDate());
    }
}
