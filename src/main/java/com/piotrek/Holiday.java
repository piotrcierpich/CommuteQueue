package com.piotrek;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-11-10.
 */
interface Holiday {
    LocalDate getDate();
    boolean matches(Holiday other);
}
