package com.clush.test.calendar.util;

import java.time.*;

public class CalendarUtil {
    public static LocalDateTime getFirstDayOfMonth() {
        LocalDate firstDay = YearMonth.now().atDay(1);
        return LocalDateTime.of(firstDay, LocalTime.MIN);
    }

    public static LocalDateTime getLastDayOfMonth() {
        LocalDate lastDay = YearMonth.now().atEndOfMonth();
        return LocalDateTime.of(lastDay, LocalTime.MAX);
    }
}
