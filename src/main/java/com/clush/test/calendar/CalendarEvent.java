package com.clush.test.calendar;

import com.clush.test.common.BaseEntity;
import com.clush.test.member.Member;
import com.clush.test.todo.TodoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CalendarEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALENDAR_EVENT_ID")
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    public CalendarEvent(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CalendarEventDto entityToDto() {
        return CalendarEventDto.builder()
                .title(this.title)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
