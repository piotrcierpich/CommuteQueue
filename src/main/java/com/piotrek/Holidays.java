package com.piotrek;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Piotrek on 2016-10-24.
 */
class Holidays {
    // HashMap
    private final HashMap<LocalDate, HolidayCollection> daysOff = new HashMap<>();

    void add(Holiday holiday) {
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
