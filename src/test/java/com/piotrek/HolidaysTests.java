package com.piotrek;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Piotrek on 2016-11-10.
 */
public class HolidaysTests {
    LocalDate date = LocalDate.of(2016, 10, 1);
    Driver driver = new Driver("PC");

    @Test
    public void hasIsTrueWhenDriverHasHoliday() {
        //given
        Holidays sut = new Holidays();
        sut.add(new DayOff(date, driver));
        //when
        boolean result = sut.has(new DayOff(date, driver));
        //expected
        assertTrue(result);
    }

    @Test
    public void hasIsTrueWhenPublicHoliday() {
        //given
        Holidays sut = new Holidays();
        sut.add(new PublicHoliday(date));
        //when
        assertEquals(new PublicHoliday(date).hashCode(), new DayOff(date, driver).hashCode());
        assertEquals(new PublicHoliday(date).hashCode(), date.hashCode());
        boolean result = sut.has(new DayOff(date, driver));
        //expected
        assertTrue(result);
    }

    @Test
    public void hasIsTrueWhenPublicHolidayIsNotOverridenByDayOff() {
        //given
        Holidays sut = new Holidays();
        Driver otherDriver = new Driver("PA");
        sut.add(new PublicHoliday(date));
        sut.add(new DayOff(date, otherDriver));
        //when
        boolean result = sut.has(new DayOff(date,driver));
        //expected
        Assert.assertTrue(result);
    }

    @Test
    public void hasIsTrueWhenPublicHolidayOthersHaveHolidaysToo() {
        //given
        Holidays sut = new Holidays();
        Driver otherDriver = new Driver("PA");
        sut.add(new PublicHoliday(date));
        sut.add(new DayOff(date, otherDriver));
        //when
        boolean result = sut.has(new DayOff(date,driver));
        //expected
        Assert.assertTrue(result);
    }
}
