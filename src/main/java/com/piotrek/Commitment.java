package com.piotrek;

import java.time.LocalDate;

/**
 * Created by Piotrek on 2017-01-29.
 */

public interface Commitment {
    boolean TryApply(DrivePlan drivePlan, DrivingRegistry drivingRegistry);
}

class NoCommitment implements Commitment
{
    @Override
    public boolean TryApply(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        return false;
    }
}


class CommitNoCommute implements Commitment
{
    private final Driver driver;

    public CommitNoCommute(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean TryApply(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        drivingRegistry.addDrive(driver);
        return false;
    }
}

class ReadyToDrive implements Commitment{
    private final LocalDate date;
    private final Driver driver;

    public ReadyToDrive(LocalDate date, Driver driver) {
        this.date = date;
        this.driver = driver;
    }

    @Override
    public boolean TryApply(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        drivePlan.addDriveDay(new DriveDay(date, driver));
        drivingRegistry.addDrive(driver);
        return true;
    }
}