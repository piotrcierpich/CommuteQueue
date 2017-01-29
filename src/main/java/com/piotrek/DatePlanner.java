package com.piotrek;

import java.time.LocalDate;
import java.util.Arrays;

class DatePlanner {

    private final NextToDrive nextToDrive;
    private final DrivingRegistry drivingRegistry;
    private final Holidays holidays;

    DatePlanner(NextToDrive nextToDrive, DrivingRegistry drivingRegistry, Holidays holidays) {
        this.nextToDrive = nextToDrive;
        this.drivingRegistry = drivingRegistry;
        this.holidays = holidays;
    }

    void PlanTheDay(DrivePlan drivePlan, LocalDate date) {
        DrivingQueue drivingQueue = nextToDrive.find(drivingRegistry);
//        Driver driver = drivingQueue.selectAvailable(holidays, date);
//        if (driver == null)
//            return;
//        drivePlan.addDriveDay(new DriveDay(date, driver));
//        drivingRegistry.addDrive(driver);
        drivingQueue.commit(holidays, date, drivePlan, drivingRegistry);
    }
}

class DrivingQueue {
    private final Driver[] nextToDrive;

    DrivingQueue(Driver[] nextToDrive) {
        this.nextToDrive = nextToDrive;
    }

    void commit(Holidays holidays, LocalDate date, DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        for (Driver driver : nextToDrive) {
            Commitment commitment = holidays.getCommitment(date, driver);
            if(commitment.TryApply(drivePlan, drivingRegistry))
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
