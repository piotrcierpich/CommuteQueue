package com.piotrek;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-10-24.
 */
class DayOff implements Holiday {
    private final LocalDate date;
    private final Driver driver;

    DayOff(@NotNull LocalDate date, @NotNull Driver driver) {
        if(date == null)
            throw new IllegalArgumentException();
        if(driver == null)
            throw new IllegalArgumentException();

        this.date = date;
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayOff dayOff = (DayOff) o;

        if (!date.equals(dayOff.date)) return false;
        return driver != null ? driver.equals(dayOff.driver) : dayOff.driver == null;

    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean matches(Holiday other) {
        return equals(other);
    }
}
