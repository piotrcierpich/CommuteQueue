package com.piotrek.commitments;

import com.piotrek.Driver;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-11-10.
 */
public interface Excuse {
    LocalDate getDate();
    boolean matches(LocalDate date, Driver driver);
    Commitment getCommitment();
}
