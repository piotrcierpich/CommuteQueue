package com.piotrek;

import java.time.LocalDate;
import java.util.Arrays;

class DatePlanner {

    private final DriverQueue driverQueue;
    private final DrivingRegistry drivingRegistry;
    private final Holidays holidays;

    DatePlanner(DriverQueue driverQueue, DrivingRegistry drivingRegistry, Holidays holidays) {
        this.driverQueue = driverQueue;
        this.drivingRegistry = drivingRegistry;
        this.holidays = holidays;
    }

    void PlanTheDay(DrivePlan drivePlan, LocalDate date) {
        DriversByRegistry nextToDrive1 = driverQueue.next(drivingRegistry);
        Driver driver = nextToDrive1.selectAvailable(holidays, date);
        if (driver == null)
            return;

        drivePlan.addDriveDay(new DriveDay(date, driver));
        drivingRegistry.addDrive(driver);
    }
}

class DriversByRegistry {
    private final Driver[] nextToDrive;

    DriversByRegistry(Driver[] nextToDrive) {
        this.nextToDrive = nextToDrive;
    }

    Driver selectAvailable(Holidays holidays, LocalDate date) {
        for (Driver driver : nextToDrive) {
            if (holidays.has(new DayOff(date, driver)))
                continue;
            return driver;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriversByRegistry that = (DriversByRegistry) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(nextToDrive, that.nextToDrive);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nextToDrive);
    }
}
