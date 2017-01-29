package com.piotrek;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import static org.junit.Assert.*;


public class CommutePlannerTests {

    // TODO refactor towards CommutePlannerIntegrationFixture
    private static final DaysOfWeek tuesdayAndWednesday = DaysOfWeek.Is(DayOfWeek.TUESDAY).And(DayOfWeek.WEDNESDAY);
    private static final LocalDate calendarStart = LocalDate.of(2016, 10, 14);
    private final CommuteCalendarTestBuilder commuteCalendarTestBuilder;
    private final CommutePlannerFixture fixture;
    private final CommutePlanner sut;

    public CommutePlannerTests() {
        Collection<Map.Entry<String, Integer>> driverNamesAndInitialCount = new ArrayList<>();
        driverNamesAndInitialCount.add(new SimpleEntry<>("PH", 4));
        driverNamesAndInitialCount.add(new SimpleEntry<>("PA", 3));
        driverNamesAndInitialCount.add(new SimpleEntry<>("PC", 3));

        fixture = new CommutePlannerFixture(tuesdayAndWednesday, driverNamesAndInitialCount);
        commuteCalendarTestBuilder = new CommuteCalendarTestBuilder(calendarStart);
        sut = fixture.createSut();
    }

    @Test
    public void PlanShouldDequeueNextDriverForDateSpan() throws InvalidArgumentException {
        //given
        CommuteCalendar commuteCalendar = commuteCalendarTestBuilder.forDuration(Period.ofWeeks(2)).build();
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
    public void PlanShouldDequeueNextDriversConsideringHolidays() throws InvalidArgumentException {
        //given
        CommuteCalendar commuteCalendar = commuteCalendarTestBuilder.forDuration(Period.ofMonths(1)).build();
        fixture.addHoliday(new PublicHoliday(LocalDate.of(2016, 11, 1)));
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
    public void PlanShouldSkipDriverWhenHasDayOffAndUseHimWithNextRound() throws InvalidArgumentException {
        //given
        CommuteCalendar commuteCalendar = commuteCalendarTestBuilder.forDuration(Period.ofWeeks(2)).build();
        DayOff dayOff = new DayOff(LocalDate.of(2016, 10, 19), new Driver("PC"));
        fixture.addHoliday(dayOff);
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
        CommuteCalendar commuteCalendar = commuteCalendarTestBuilder.forDuration(Period.ofMonths(1)).build();
        PublicHoliday publicHoliday = new PublicHoliday(LocalDate.of(2016, 11, 1));
        fixture.addHoliday(publicHoliday);
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
        CommuteCalendar commuteCalendar = commuteCalendarTestBuilder.forDuration(Period.ofWeeks(2)).build();
        Holiday outOfQueue = new OutOfQueue(LocalDate.of(2016, 10, 19), new Driver("PC"));
        fixture.addHoliday(outOfQueue);
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

class CommuteCalendarTestBuilder {
    private final LocalDate calendarStart;
    private TemporalAmount calendarDuration;

    CommuteCalendarTestBuilder(LocalDate calendarStart) {
        this.calendarStart = calendarStart;
    }

    CommuteCalendarTestBuilder forDuration(TemporalAmount duration) {
        calendarDuration = duration;
        return this;
    }

    CommuteCalendar build() throws InvalidArgumentException {
        LocalDate calendarEnd = calendarStart.plus(calendarDuration);
        DateSpan dateSpan = DateSpan.between(calendarStart, calendarEnd);
        return new CommuteCalendar(dateSpan);
    }
}

class CommutePlannerFixture {
    private final DaysOfWeek daysOfWeek;
    private final Holidays holidays = new Holidays();
    private final Collection<Map.Entry<String, Integer>> driverNameAndCount;

    CommutePlannerFixture(DaysOfWeek daysOfWeek, Collection<Map.Entry<String, Integer>> driverNameAndCount) {
        this.daysOfWeek = daysOfWeek;
        this.driverNameAndCount = driverNameAndCount;
    }

    CommutePlanner createSut() {
        DatePlanner datePlanner = new DatePlanner(createDriverQueue(), createDrivingRegistry(), holidays);
        return new CommutePlanner(daysOfWeek, datePlanner);
    }

    private NextToDrive createDriverQueue() {
        String[] drivers = driverNameAndCount.stream().map(Map.Entry::getKey).toArray(String[]::new);
        DriversOrdered driversOrdered = new DriversOrdered(drivers);
        return new NextToDrive(driversOrdered);
    }

    private DrivingRegistry createDrivingRegistry() {
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        driverNameAndCount.forEach(e -> drivingRegistry.set(new Driver(e.getKey()), e.getValue()));
        return drivingRegistry;
    }

    void addHoliday(Holiday holiday) {
        holidays.add(holiday);
    }
}