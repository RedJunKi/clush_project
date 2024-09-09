package com.clush.test.domain.calendar.entity;

import com.clush.test.domain.member.entity.MemberPostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SharedCalendarEventResponse {
    private Long id;
    private CalendarEventDto calendarEvent;
    private MemberPostDto memberPostDto;

    public SharedCalendarEventResponse(SharedCalendarEvent sharedCalendarEvent) {
        this.id = sharedCalendarEvent.getId();
        this.calendarEvent = sharedCalendarEvent.getCalendarEvent().entityToDto();
        this.memberPostDto = sharedCalendarEvent.getMember().entityToDto();
    }
}
