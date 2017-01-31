package com.piotrek.calendar;

import java.time.DayOfWeek;
import java.util.HashSet;

/**
 * Created by Piotrek on 2016-10-14.
 */
public final class DaysOfWeek {
    private final HashSet<DayOfWeek> daysOfWeek = new HashSet<>();

    private DaysOfWeek(DayOfWeek dayOfWeek){
        daysOfWeek.add(dayOfWeek);
    }

    public static DaysOfWeek Is(DayOfWeek dayOfWeek) {
        return new DaysOfWeek(dayOfWeek);
    }

    public DaysOfWeek And(DayOfWeek dayOfWeek) {
        daysOfWeek.add(dayOfWeek);
        return this;
    }

    public boolean notContains(DayOfWeek dayOfWeek) {
        return !daysOfWeek.contains(dayOfWeek);
    }
}
