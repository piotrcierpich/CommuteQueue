package com.piotrek;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-12-08.
 */
class OutOfQueue implements Holiday {
    private final LocalDate date;
    private final Driver driver;

    OutOfQueue(@NotNull LocalDate date, @NotNull Driver driver)  {
        this.date = date;
        this.driver = driver;
    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public boolean matches(Holiday other) {
        return false;
    }
}
