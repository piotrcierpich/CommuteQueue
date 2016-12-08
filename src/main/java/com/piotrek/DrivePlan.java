package com.piotrek;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2016-10-15.
 */
class DrivePlan {
    private final List<DriveDay> driveDays = new ArrayList<>();

    DriveDay[] getDriveDays() {
        DriveDay[] driveDaysAsArray = new DriveDay[driveDays.size()];
        driveDaysAsArray = driveDays.toArray(driveDaysAsArray);
        return driveDaysAsArray;
    }

    void addDriveDay(DriveDay driveDay) {
        driveDays.add(driveDay);
    }
}
