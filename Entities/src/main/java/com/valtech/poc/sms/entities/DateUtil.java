package com.valtech.poc.sms.entities;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= numOfDaysBetween; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }
}