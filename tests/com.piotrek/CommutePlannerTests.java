package com.piotrek;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Piotrek on 2016-10-14.
 */
public class CommutePlannerTests {

    private final DaysOfWeek daysOfWeek = DaysOfWeek.Is(DayOfWeek.TUESDAY).And(DayOfWeek.WEDNESDAY);
    private final LocalDate saturday = LocalDate.of(2016, 10, 14);
    private final CommutePlanner sut;
    private final Holidays holidays = new Holidays();

    public CommutePlannerTests() {
        DatePlanner datePlanner = new DatePlanner(CreateDriverQueue(), CreateDrivingRegistry(), holidays);
        this.sut = new CommutePlanner(daysOfWeek, datePlanner);
    }

    private DriverQueue CreateDriverQueue() {
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        return new DriverQueue(driversOrdered);
    }

    private DrivingRegistry CreateDrivingRegistry() {
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 4);
        drivingRegistry.set(new Driver("PA"), 3);
        drivingRegistry.set(new Driver("PC"), 3);
        return drivingRegistry;
    }

    @Test
    public void PlanShouldDequeueNextDriverForDateSpan() throws InvalidArgumentException {
        //given
        LocalDate saturdayInTwoWeeks = saturday.plusWeeks(2);
        DateSpan dateSpan = DateSpan.between(saturday, saturdayInTwoWeeks);
        CommuteCalendar commuteCalendar = new CommuteCalendar(dateSpan);
        //when
        DrivePlan drivePlan = sut.plan(commuteCalendar);
        //expected
        DriveDay[] driveDays = drivePlan.getDriveDays();
        DriveDay[] expectedDriveDays = new DriveDay[]{
                new DriveDay(LocalDate.of(2016, 10, 18), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 19), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 10, 25), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 10, 26), new Driver("PA")),
        };

        assertArrayEquals(expectedDriveDays, driveDays);
    }

    @Test
    public void PlanShouldDequeueNextDriversConsideringHolidays() throws InvalidArgumentException{
        //given
        LocalDate saturdayInAMonth = saturday.plusMonths(1);
        DateSpan dateSpan = DateSpan.between(saturday, saturdayInAMonth);
        CommuteCalendar commuteCalendar = new CommuteCalendar(dateSpan);
        // TODO remove
//        LocalDate[] holidays = new LocalDate[]{LocalDate.of(2016, 11, 1)};
        holidays.add(new PublicHoliday(LocalDate.of(2016, 11, 1)));
        //when
        DrivePlan drivePlan = sut.plan(commuteCalendar);
        //expected
        DriveDay[] driveDays = drivePlan.getDriveDays();
        DriveDay[] expectedDriveDays = new DriveDay[]{
                new DriveDay(LocalDate.of(2016, 10, 18), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 19), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 10, 25), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 10, 26), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 11, 2), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 11, 8), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 11, 9), new Driver("PA")),
        };

        assertArrayEquals(expectedDriveDays, driveDays);
    }

    //TODO simply interface to specify holidays as part of the Holidays class - CommutePlanner
    // if(holidays.has(new DayOff(date, driver)))
                // continue;
    @Test
    public void PlanShouldSkipDriverWhenHasDayOffAndUseHimWithNextRound() throws InvalidArgumentException {
        //given
        LocalDate saturdayInAWeek = saturday.plusWeeks(2);
        DateSpan dateSpan = DateSpan.between(saturday, saturdayInAWeek);
        CommuteCalendar commuteCalendar = new CommuteCalendar(dateSpan);
        DayOff dayOff = new DayOff(LocalDate.of(2016, 10, 19),  new Driver("PC"));
        holidays.add(dayOff);
        //when
        DrivePlan drivePlan = sut.plan(commuteCalendar);
        //expected
        DriveDay[] driveDays = drivePlan.getDriveDays();
        DriveDay[] expectedDriveDays = new DriveDay[]{
                new DriveDay(LocalDate.of(2016, 10, 18), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 19), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 10, 25), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 10, 26), new Driver("PA"))
        };
        assertArrayEquals(expectedDriveDays, driveDays);
    }

    @Test
    public void PlanShouldSkipeWhenHolidays() throws InvalidArgumentException {
        //given
        LocalDate saturdayInAMonth = saturday.plusMonths(1);
        DateSpan dateSpan = DateSpan.between(saturday, saturdayInAMonth);
        CommuteCalendar commuteCalendar = new CommuteCalendar(dateSpan);
        PublicHoliday publicHoliday = new PublicHoliday(LocalDate.of(2016, 11, 1));
        holidays.add(publicHoliday);
        //when
        DrivePlan drivePlan = sut.plan(commuteCalendar);
        //expected
        DriveDay[] driveDays = drivePlan.getDriveDays();
        DriveDay[] expectedDriveDays = new DriveDay[]{
                new DriveDay(LocalDate.of(2016, 10, 18), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 19), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 10, 25), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 10, 26), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 11, 2), new Driver("PC")),
                new DriveDay(LocalDate.of(2016, 11, 8), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 11, 9), new Driver("PA")),
        };

        assertArrayEquals(expectedDriveDays, driveDays);
    }

    @Test
    public void PlanShouldSkipThoseThatPause() throws InvalidArgumentException {
        //given
        LocalDate saturdayIn2weeks = saturday.plusWeeks(2);
        DateSpan dateSpan = DateSpan.between(saturday, saturdayIn2weeks);
        CommuteCalendar commuteCalendar = new CommuteCalendar(dateSpan);
        Holiday outOfQueue = new OutOfQueue(LocalDate.of(2016, 11, 1), new Driver("PC"));
        holidays.add(outOfQueue);
        //when
        DrivePlan drivePlan = sut.plan(commuteCalendar);
        //expected
        DriveDay[] driveDays = drivePlan.getDriveDays();
        DriveDay[] expectedDriveDays = new DriveDay[]{
                new DriveDay(LocalDate.of(2016, 10, 18), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 19), new Driver("PH")),
                new DriveDay(LocalDate.of(2016, 10, 25), new Driver("PA")),
                new DriveDay(LocalDate.of(2016, 10, 26), new Driver("PC")),
        };

        assertArrayEquals(expectedDriveDays, driveDays);
    }

}