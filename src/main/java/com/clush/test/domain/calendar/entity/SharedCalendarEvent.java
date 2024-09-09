package com.clush.test.domain.calendar.entity;

import com.clush.test.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharedCalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHARED_CALENDAR_EVENT_ID")
    private Long id;

    @ManyToOne
    private CalendarEvent calendarEvent;

    @ManyToOne
    private Member member;
}
