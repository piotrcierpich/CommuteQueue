package com.piotrek.Commitments;

import com.piotrek.DrivePlan;
import com.piotrek.Driver;
import com.piotrek.statistics.DrivingRegistry;

class CommitNoCommute implements Commitment
{
    private final Driver driver;

    CommitNoCommute(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean TryFulfillPlan(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        drivingRegistry.addDrive(driver);
        return false;
    }
}
