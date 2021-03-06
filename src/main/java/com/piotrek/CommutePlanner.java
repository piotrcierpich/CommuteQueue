package com.piotrek;

import com.piotrek.calendar.DaysOfWeek;
import com.piotrek.planning.DatePlanner;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2016-10-15.
 */
class CommutePlanner {
    private final DaysOfWeek daysOfWeek;
    private final DatePlanner datePlanner;

    CommutePlanner(DaysOfWeek daysOfWeek, DatePlanner datePlanner) {

        this.daysOfWeek = daysOfWeek;
        this.datePlanner = datePlanner;
    }

    DrivePlan plan(CommuteCalendar commuteCalendar) {
        LocalDate[] dates = commuteCalendar.getDates();
        DrivePlan drivePlan = new DrivePlan();
        for (LocalDate date : dates){
            if(daysOfWeek.notContains(date.getDayOfWeek()))
                continue;
            datePlanner.planTheDay(drivePlan, date);
        }
        return drivePlan;
    }
}

