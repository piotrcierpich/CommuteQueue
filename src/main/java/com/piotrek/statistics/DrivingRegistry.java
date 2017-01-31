package com.piotrek.statistics;

import com.piotrek.Driver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Piotrek on 2016-10-09.
 */
public class DrivingRegistry {
    private final Map<Driver, Integer> countMap = new HashMap<>();
    private final Map<Driver, Integer> outOfQueueMap = new HashMap<>();

    public void set(Driver driver, int count) {
        countMap.put(driver, count);
    }

    public DriverCount getDriverCounts(Driver driver) {
        Integer driverCountWhenExist = countMap.getOrDefault(driver, 0);
        Integer outOfQueueCountWhenExist = outOfQueueMap.getOrDefault(driver, 0);
        return new DriverCount(driver, driverCountWhenExist + outOfQueueCountWhenExist);
    }

    public void addDrive(Driver driver) {
        Integer driveCountsIncreasedByOne = countMap.getOrDefault(driver, 0) + 1;
        countMap.put(driver, driveCountsIncreasedByOne);
    }

    public void addOutOfQueue(Driver driver) {
        Integer outOfQueuePlusOne = outOfQueueMap.getOrDefault(driver, 0) + 1;
        outOfQueueMap.put(driver, outOfQueuePlusOne);
    }
}

