package com.piotrek.planning;

import com.piotrek.commitments.Readiness;
import com.piotrek.DrivePlan;
import com.piotrek.statistics.DrivingRegistry;

import java.time.LocalDate;

public class DatePlanner {

    private final NextToDrive nextToDrive;
    private final DrivingRegistry drivingRegistry;
    private final Readiness readiness;

    public DatePlanner(NextToDrive nextToDrive, DrivingRegistry drivingRegistry, Readiness readiness) {
        this.nextToDrive = nextToDrive;
        this.drivingRegistry = drivingRegistry;
        this.readiness = readiness;
    }

    public void planTheDay(DrivePlan drivePlan, LocalDate date) {
        DrivingQueue drivingQueue = nextToDrive.find(drivingRegistry);
        drivingQueue.commit(readiness, date, drivePlan, drivingRegistry);
    }
}

