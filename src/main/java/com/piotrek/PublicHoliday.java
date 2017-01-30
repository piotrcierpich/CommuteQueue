package com.piotrek;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-11-10.
 */
class PublicHoliday implements Excuse {
    private final LocalDate date;

    PublicHoliday(LocalDate date) {
        if(date == null)
            throw new IllegalArgumentException();

        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicHoliday that = (PublicHoliday) o;

        return date.equals(that.date);

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
    public boolean matches(Excuse other) {
        return date.isEqual(other.getDate());
    }
}
