package com.piotrek.planning;

import com.piotrek.Commitments.Commitment;
import com.piotrek.Commitments.Readiness;
import com.piotrek.DrivePlan;
import com.piotrek.Driver;
import com.piotrek.statistics.DrivingRegistry;

import java.time.LocalDate;
import java.util.Arrays;

public class DrivingQueue {
    private final Driver[] nextToDrive;

    public DrivingQueue(Driver[] nextToDrive) {
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
