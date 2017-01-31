package com.piotrek;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.*;

import static javax.swing.UIManager.get;

/**
 * Created by Piotrek on 2016-10-24.
 */
class Readiness {
//    private final HashSet<DriveDay> outOfQueue = new HashSet<>();
    private final HashMap<LocalDate, Collection<Excuse>> excuses = new HashMap<>();

    void add(Excuse excuse) {
        if(!excuses.containsKey(excuse.getDate())){
            Collection<Excuse> excusesCollection = new ArrayList<>();
            excuses.put(excuse.getDate(), excusesCollection);
        }
        excuses.get(excuse.getDate()).add(excuse);
//
//        if(excuse instanceof OutOfQueue){
//            OutOfQueue outOfQueueInstance = (OutOfQueue) excuse;
//            DriveDay driveDay = new DriveDay(outOfQueueInstance.getDate(),outOfQueueInstance.getDriver());
//            outOfQueue.add(driveDay);
//            return;
//        }
//
//        HolidayCollection holidayCollection = findHolidayCollection(excuse);
//        holidayCollection.add(excuse);
    }

//    private HolidayCollection findHolidayCollection(Excuse excuse) {
//        HolidayCollection holidayCollection;
//        if (daysOff.containsKey(excuse.getDate())) {
//            holidayCollection = daysOff.get(excuse.getDate());
//        } else {
//            holidayCollection = new HolidayCollection();
//            daysOff.put(excuse.getDate(), holidayCollection);
//        }
//        return holidayCollection;
//    }
//
//    boolean has(Excuse excuse) {
//        if(!daysOff.containsKey(excuse.getDate()))
//            return false;
//        HolidayCollection existingHolidays = daysOff.get(excuse.getDate());
//        return existingHolidays.contains(excuse);
//    }

    Commitment getCommitment(LocalDate date, Driver driver) {
        if(!excuses.containsKey(date))
            return new ReadyToDrive(date, driver);

        Optional<Excuse> excuseOptional = excuses.get(date)
                                                 .stream()
                                                 .filter(e -> e.matches(date, driver))
                                                 .findFirst();
        if(!excuseOptional.isPresent())
            return new ReadyToDrive(date, driver);

        return excuseOptional.get().getCommitment();
    }
}
