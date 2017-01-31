package com.piotrek;

import com.piotrek.Commitments.Commitment;
import com.piotrek.Commitments.Readiness;

import java.time.LocalDate;
import java.util.Arrays;

class DatePlanner {

    private final NextToDrive nextToDrive;
    private final DrivingRegistry drivingRegistry;
    private final Readiness readiness;

    DatePlanner(NextToDrive nextToDrive, DrivingRegistry drivingRegistry, Readiness readiness) {
        this.nextToDrive = nextToDrive;
        this.drivingRegistry = drivingRegistry;
        this.readiness = readiness;
    }

    void PlanTheDay(DrivePlan drivePlan, LocalDate date) {
        DrivingQueue drivingQueue = nextToDrive.find(drivingRegistry);
        drivingQueue.commit(readiness, date, drivePlan, drivingRegistry);
    }
}

class DrivingQueue {
    private final Driver[] nextToDrive;

    DrivingQueue(Driver[] nextToDrive) {
        this.nextToDrive = nextToDrive;
    }

    void commit(Readiness readiness, LocalDate date, DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        for (Driver driver : nextToDrive) {
            Commitment commitment = readiness.getCommitment(date, driver);
            if(commitment.TryFulfillPlan(drivePlan, drivingRegistry))
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrivingQueue that = (DrivingQueue) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(nextToDrive, that.nextToDrive);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nextToDrive);
    }
}
