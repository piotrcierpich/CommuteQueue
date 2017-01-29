package com.piotrek;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Piotrek on 2016-10-24.
 */
class Holidays {
    // HashMap
    private final HashMap<LocalDate, HolidayCollection> daysOff = new HashMap<>();
    private final HashSet<DriveDay> outOfQueue = new HashSet<>();
//    private final HashMap<>


    void add(Holiday holiday) {
        if(holiday instanceof OutOfQueue){
            OutOfQueue outOfQueueInstance = (OutOfQueue)holiday;
            DriveDay driveDay = new DriveDay(outOfQueueInstance.getDate(),outOfQueueInstance.getDriver());
            outOfQueue.add(driveDay);
            return;
        }

        HolidayCollection holidayCollection = findHolidayCollection(holiday);
        holidayCollection.add(holiday);
    }

    private HolidayCollection findHolidayCollection(Holiday holiday) {
        HolidayCollection holidayCollection;
        if (daysOff.containsKey(holiday.getDate())) {
            holidayCollection = daysOff.get(holiday.getDate());
        } else {
            holidayCollection = new HolidayCollection();
            daysOff.put(holiday.getDate(), holidayCollection);
        }
        return holidayCollection;
    }

    boolean has(Holiday holiday) {
        if(!daysOff.containsKey(holiday.getDate()))
            return false;
        HolidayCollection existingHolidays = daysOff.get(holiday.getDate());
        return existingHolidays.contains(holiday);
    }

    public Commitment getCommitment(LocalDate date, Driver driver) {
        if(has(new DayOff(date, driver)))
            return new NoCommitment();
        if(outOfQueue.contains(new DriveDay(date, driver)))
            return new CommitNoCommute(driver);
        return new ReadyToDrive(date, driver);
    }

    private class HolidayCollection{
        private Collection<Holiday> holidays = new ArrayList<>();

        void add(Holiday holiday){
            holidays.add(holiday);
        }

        boolean contains(Holiday holiday){
            return holidays.stream().anyMatch(h -> h.matches(holiday));
        }
    }
}
