package com.piotrek;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-10-15.
 */
public class DriveDay {
    private final LocalDate date;
    private final Driver driver;

    public DriveDay(LocalDate date, Driver driver) {

        this.date = date;
        this.driver = driver;
    }

    public LocalDate getDate() {
        return date;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriveDay driveDay = (DriveDay) o;

        if (!date.equals(driveDay.date)) return false;
        return driver.equals(driveDay.driver);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + driver.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return driver + " drives at " + date;
    }
}
