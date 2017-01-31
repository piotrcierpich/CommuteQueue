package com.piotrek;

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

    static DaysOfWeek Is(DayOfWeek dayOfWeek) {
        return new DaysOfWeek(dayOfWeek);
    }

    DaysOfWeek And(DayOfWeek dayOfWeek) {
        daysOfWeek.add(dayOfWeek);
        return this;
    }

    boolean notContains(DayOfWeek dayOfWeek) {
        return !daysOfWeek.contains(dayOfWeek);
    }
}
