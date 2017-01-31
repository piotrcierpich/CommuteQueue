package com.piotrek;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Piotrek on 2016-10-09.
 */
class DrivingRegistry {
    private final Map<Driver, Integer> countMap = new HashMap<>();
    private final Map<Driver, Integer> outOfQueueMap = new HashMap<>();

    void set(Driver driver, int count) {
        countMap.put(driver, count);
    }

    DriverCount getDriverCounts(Driver driver) {
        Integer driverCountWhenExist = countMap.getOrDefault(driver, 0);
        Integer outOfQueueCountWhenExist = outOfQueueMap.getOrDefault(driver, 0);
        return new DriverCount(driver, driverCountWhenExist + outOfQueueCountWhenExist);
    }

    void addDrive(Driver driver) {
        Integer driveCountsIncreasedByOne = countMap.getOrDefault(driver, 0) + 1;
        countMap.put(driver, driveCountsIncreasedByOne);
    }

    void addOutOfQueue(Driver driver) {
        Integer outOfQueuePlusOne = outOfQueueMap.getOrDefault(driver, 0) + 1;
        outOfQueueMap.put(driver, outOfQueuePlusOne);
    }
}

class DriverCount implements Comparable<DriverCount> {
    private final Integer count;
    private final Driver driver;

    DriverCount(Driver driver, Integer count) {
        this.count = count;
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public int compareTo(@NotNull DriverCount other) {
        return count.compareTo(other.count);
    }
}
