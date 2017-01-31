package com.piotrek;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by Piotrek on 2016-10-20.
 */
class CommuteCalendar {
    private final DateSpan dateSpan;

    CommuteCalendar(DateSpan dateSpan) {
        this.dateSpan = dateSpan;
    }

    LocalDate[] getDates() {
        return Arrays.stream(dateSpan.getDates())
                .toArray(size -> new LocalDate[size]);
    }
}
