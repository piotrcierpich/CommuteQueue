package com.piotrek;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-11-10.
 */
interface Excuse {
    LocalDate getDate();
    boolean matches(LocalDate date, Driver driver);
    Commitment getCommitment();
}
