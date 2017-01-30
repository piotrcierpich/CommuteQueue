package com.piotrek;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Piotrek on 2016-10-15.
 */
public class DateSpanTest {
    @Test
    public void Period() {
        //given
        LocalDate date = LocalDate.of(2016, 10, 15);
        LocalDate dateMonthLater = date.plusMonths(1);
        //when
        long daysCount = ChronoUnit.DAYS.between(date, dateMonthLater);
        //expected
        assertEquals(31, daysCount);
    }

    @Test(expected=InvalidArgumentException.class)
    public void ctorShouldThrowWhenEndDateIsEarlierThanBegin() throws InvalidArgumentException{
        //given
        LocalDate begin = LocalDate.of(2016, 10, 15);
        LocalDate end = LocalDate.of(2016, 10, 14);
        //when
        DateSpan sut = DateSpan.between(begin, end);
    }

    @Test
    public void getDates() throws Exception {
        //given
        LocalDate date1 = LocalDate.of(2016, 10, 15);
        LocalDate date2 = LocalDate.of(2016, 10, 18);
        DateSpan sut = DateSpan.between(date1, date2);
        //when
        LocalDate[] result = sut.getDates();
        //expected
        LocalDate[] expectedDates = new LocalDate[]{
                LocalDate.of(2016, 10, 15),
                LocalDate.of(2016, 10, 16),
                LocalDate.of(2016, 10, 17),
                LocalDate.of(2016, 10, 18)
        };
        assertArrayEquals(expectedDates, result);
    }


    @Test
    public void getSingleDate() throws Exception {
        //given
        LocalDate date1 = LocalDate.of(2016, 10, 15);
        LocalDate date2 = LocalDate.of(2016, 10, 15);
        DateSpan sut = DateSpan.between(date1, date2);
        //when
        LocalDate[] result = sut.getDates();
        //expected
        LocalDate[] expectedDates = new LocalDate[]{
                LocalDate.of(2016, 10, 15),
        };
        assertArrayEquals(expectedDates, result);
    }

    @Test
    public void getDaysForWholeMonth() throws InvalidArgumentException{
        //given
        LocalDate date = LocalDate.of(2016, 10, 15);
        LocalDate dateInAMonth = date.plusMonths(1);
        DateSpan sut = DateSpan.between(date, dateInAMonth);
        //when
        LocalDate[] result = sut.getDates();
        //expected
        List<LocalDate> expectedDates = new ArrayList<>();
        for (LocalDate l = date; l.isAfter(dateInAMonth) == false; l = l.plusDays(1)){
            expectedDates.add(l);
        }
        assertArrayEquals(expectedDates.toArray(), result);
    }
}