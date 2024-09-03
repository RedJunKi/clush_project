package com.clush.test.calendar;

import com.clush.test.common.BaseEntity;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class CalendarEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALENDAR_EVENT_ID")
    private long id;
    private String title;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;

}
