package com.sourcery.clinicapp.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeHelper {

    public static LocalDateTime toDateTime(String date, String time) {
        return LocalDateTime.parse(date + "T" + time);
    }

    public static LocalDateTime fromDateString(String date) {
        return LocalDate.parse(date).atStartOfDay();
    }

    public static LocalDateTime nextMonthFirstDay(LocalDateTime dateTime) {
        LocalDateTime addedMonth = dateTime.plusMonths(1);
        return LocalDateTime.of(addedMonth.getYear(), addedMonth.getMonthValue(), 1, 0, 0);
    }

    public static LocalDate toDate(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int monthValue = dateTime.getMonthValue();
        int dayOfMonth = dateTime.getDayOfMonth();
        return LocalDate.of(year, monthValue, dayOfMonth);
    }

    public static LocalTime toTime(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        return LocalTime.of(hour, minute);
    }

    public static String toDateString(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int monthValue = dateTime.getMonthValue();
        int dayOfMonth = dateTime.getDayOfMonth();
        return LocalDate.of(year, monthValue, dayOfMonth).toString();
    }

    public static String toTimeString(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        return LocalTime.of(hour, minute).toString();
    }
}
