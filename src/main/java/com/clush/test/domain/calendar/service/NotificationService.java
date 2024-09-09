package com.clush.test.domain.calendar.service;

import com.clush.test.domain.calendar.entity.CalendarEvent;
import com.clush.test.domain.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final CalendarRepository calendarRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkEndDates() {
        LocalDate today = LocalDate.now();

        List<CalendarEvent> toDayEvents = calendarRepository.findByEndDate(today).orElse(null);

        if (toDayEvents == null) {
            return;
        }

        for (CalendarEvent toDayEvent : toDayEvents) {
            String email = toDayEvent.getMember().getEmail();
            emailService.sendEmail(email, "일정 종료일입니다.", "오늘은 등록한 일정의 종료일입니다. 등록된 일정을 확인해주세요.");
        }
    }
}
