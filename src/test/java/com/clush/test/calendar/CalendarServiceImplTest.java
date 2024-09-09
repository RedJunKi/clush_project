package com.clush.test.calendar;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.clush.test.domain.calendar.entity.CalendarEventDto;
import com.clush.test.domain.calendar.entity.CalendarEventResponse;
import com.clush.test.domain.calendar.repository.CalendarRepository;
import com.clush.test.domain.calendar.service.CalendarServiceImpl;
import com.clush.test.domain.member.entity.Member;
import com.clush.test.domain.member.repository.MemberRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Transactional
class CalendarServiceImplTest {

    @Autowired
    private CalendarServiceImpl calendarService;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private CalendarEvent savedCalendarEvent;

    @BeforeEach
    void setUp() {
        // 회원 생성 및 저장
        testMember = new Member();
        testMember.setEmail("testmember@example.com");
        testMember.setUsername("TestMember");
        testMember.setPassword("password");
        memberRepository.save(testMember);

        // CalendarEvent 생성 및 저장
        savedCalendarEvent = new CalendarEvent("제목", "내용", LocalDateTime.of(2024, 9, 3, 0, 0, 0), LocalDateTime.of(2024, 9, 10, 23, 59, 59), testMember);
        calendarRepository.save(savedCalendarEvent);
    }

    @Test
    void getAllEventsBetween() {
        // given
        LocalDateTime startDate = LocalDateTime.of(2024, 9, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 9, 30, 0, 0, 0);

        // when
        CalendarEventResponse calendarEvents = calendarService.getAllEventsBetween(startDate, endDate, testMember.getId());

        // then
        assertThat(calendarEvents.getCalendarEventDtos().size()).isGreaterThanOrEqualTo(1);
        assertThat(calendarEvents.getCalendarEventDtos().get(0).getTitle()).isEqualTo(savedCalendarEvent.getTitle());
    }

    @Test
    void getEventById() {
        // when
        CalendarEventDto result = calendarService.getEventById(savedCalendarEvent.getId(), testMember.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(savedCalendarEvent.getTitle());
    }

    @Test
    void addEvent() {
        // given
        CalendarEventDto newEventDto = new CalendarEventDto("새 제목", "새 내용", LocalDateTime.of(2024, 9, 12, 0, 0, 0), LocalDateTime.of(2024, 9, 20, 23, 59, 59));

        // when
        CalendarEventDto result = calendarService.addEvent(newEventDto, testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo(newEventDto.getTitle());
        assertThat(result.getEndDate()).isEqualTo(newEventDto.getEndDate());

        // DB에서 새로 추가된 이벤트가 있는지 확인
        List<CalendarEvent> events = calendarRepository.findAllByStartDateBetweenAndMemberId(newEventDto.getStartDate(), newEventDto.getEndDate(), testMember.getId());
        assertThat(events).hasSize(1);
    }

    @Test
    void updateEvent() {
        // given
        CalendarEventDto updatedEventDto = new CalendarEventDto("수정된 제목", "수정된 내용", LocalDateTime.now(), LocalDateTime.now());

        // when
        CalendarEventDto result = calendarService.updateEvent(savedCalendarEvent.getId(), updatedEventDto, testMember.getId());

        // then
        assertThat(result.getTitle()).isEqualTo(updatedEventDto.getTitle());
        assertThat(result.getStartDate()).isEqualTo(updatedEventDto.getStartDate());

        // 실제 DB에서 업데이트된 이벤트 확인
        CalendarEventDto updatedEventFromDb = calendarService.getEventById(savedCalendarEvent.getId(), testMember.getId());
        assertThat(updatedEventFromDb.getTitle()).isEqualTo(updatedEventDto.getTitle());
        assertThat(updatedEventFromDb.getDescription()).isEqualTo(updatedEventDto.getDescription());
    }

    @Test
    void deleteEvent() {
        // when
        calendarService.deleteEvent(savedCalendarEvent.getId(), testMember.getId());

        // then
        assertThatThrownBy(() -> calendarService.getEventById(savedCalendarEvent.getId(), testMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 아이디를 찾을 수 없습니다.");

        assertThatThrownBy(() -> calendarService.deleteEvent(savedCalendarEvent.getId(), testMember.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 아이디를 찾을 수 없습니다.");
    }

    @Test
    public void findByEndDate() {
        // given
        LocalDateTime birthDay = LocalDateTime.of(1995,4,14,0,0,0);
        CalendarEvent event = new CalendarEvent();
        event.setTitle("생일");
        event.setDescription("내 생일");
        event.setEndDate(birthDay);

        calendarRepository.save(event);

        // when
        LocalDate today = LocalDate.of(1995,4,14);
        List<CalendarEvent> events = calendarRepository.findByEndDate(today).get();

        // then
        assertThat(events.isEmpty()).isFalse();
        assertThat("생일").isEqualTo(events.get(0).getTitle());
    }
}