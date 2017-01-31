package com.piotrek;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-12-08.
 */
class OutOfQueue implements Excuse {
    private final LocalDate date;
    private final Driver driver;

    OutOfQueue(@NotNull LocalDate date, @NotNull Driver driver)  {
        this.date = date;
        this.driver = driver;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean matches(LocalDate date, Driver driver) {
        return this.date.isEqual(date) && this.driver.equals(driver);
    }

    @Override
    public Commitment getCommitment() {
        return new CommitNoCommute(driver);
    }

    public Driver getDriver(){
        return driver;
    }
}
