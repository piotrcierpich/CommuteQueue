package com.piotrek.commitments;

import com.piotrek.DrivePlan;
import com.piotrek.statistics.DrivingRegistry;

class NoCommitment implements Commitment
{
    @Override
    public boolean TryFulfillPlan(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        return false;
    }
}
