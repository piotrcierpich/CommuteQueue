package com.piotrek.Commitments;

import com.piotrek.DrivePlan;
import com.piotrek.DrivingRegistry;

class NoCommitment implements Commitment
{
    @Override
    public boolean TryFulfillPlan(DrivePlan drivePlan, DrivingRegistry drivingRegistry) {
        return false;
    }
}
