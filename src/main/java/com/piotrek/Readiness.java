package com.piotrek;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Piotrek on 2016-10-24.
 */
class Readiness {
    private final HashMap<LocalDate, HolidayCollection> daysOff = new HashMap<>();
    private final HashSet<DriveDay> outOfQueue = new HashSet<>();


    void add(Excuse excuse) {
        if(excuse instanceof OutOfQueue){
            OutOfQueue outOfQueueInstance = (OutOfQueue) excuse;
            DriveDay driveDay = new DriveDay(outOfQueueInstance.getDate(),outOfQueueInstance.getDriver());
            outOfQueue.add(driveDay);
            return;
        }

        HolidayCollection holidayCollection = findHolidayCollection(excuse);
        holidayCollection.add(excuse);
    }

    private HolidayCollection findHolidayCollection(Excuse excuse) {
        HolidayCollection holidayCollection;
        if (daysOff.containsKey(excuse.getDate())) {
            holidayCollection = daysOff.get(excuse.getDate());
        } else {
            holidayCollection = new HolidayCollection();
            daysOff.put(excuse.getDate(), holidayCollection);
        }
        return holidayCollection;
    }

    boolean has(Excuse excuse) {
        if(!daysOff.containsKey(excuse.getDate()))
            return false;
        HolidayCollection existingHolidays = daysOff.get(excuse.getDate());
        return existingHolidays.contains(excuse);
    }

    public Commitment getCommitment(LocalDate date, Driver driver) {
        if(has(new DayOff(date, driver)))
            return new NoCommitment();
        if(outOfQueue.contains(new DriveDay(date, driver)))
            return new CommitNoCommute(driver);
        return new ReadyToDrive(date, driver);
    }

    private class HolidayCollection{
        private Collection<Excuse> excuses = new ArrayList<>();

        void add(Excuse excuse){
            excuses.add(excuse);
        }

        boolean contains(Excuse excuse){
            return excuses.stream().anyMatch(h -> h.matches(excuse));
        }
    }
}
