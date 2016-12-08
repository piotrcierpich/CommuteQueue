package com.piotrek;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Piotrek on 2016-10-20.
 */
class CommuteCalendar {
    private final DateSpan dateSpan;
    private final HashSet<LocalDate> holidays = new HashSet<>();

    CommuteCalendar(DateSpan dateSpan) {
        this.dateSpan = dateSpan;
    }

    LocalDate[] getDates() {
        return Arrays.stream(dateSpan.getDates())
                .filter(this::NotHoliday)
                .toArray(size -> new LocalDate[size]);
    }

    private boolean NotHoliday(LocalDate date) {
        return holidays.contains(date) == false;
    }
}
