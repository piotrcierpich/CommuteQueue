package com.piotrek;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Piotrek on 2016-10-08.
 */
class NextToDrive {
    private final DriversOrdered driversOrdered;

    NextToDrive(DriversOrdered driversOrdered) {

        this.driversOrdered = driversOrdered;
    }

    DrivingQueue find(DrivingRegistry drivingRegistry) {
        List<CommutePrioritized> commutePrioritizedList = StreamSupport.stream(driversOrdered.spliterator(), false)
                                                                        .map(driver -> drivingRegistry.getDriverCounts(driver))
                                                                        .map(driverCount -> new CommutePrioritized(driverCount))
                                                                        .collect(Collectors.toList());
        Collections.sort(commutePrioritizedList);
        Driver[] drivers = commutePrioritizedList.stream()
                .map(prioritized -> prioritized.driverCount.getDriver())
                .toArray(Driver[]::new);
        return new DrivingQueue(drivers);
    }

    private class CommutePrioritized implements Comparable<CommutePrioritized> {
        private final DriverCount driverCount;

        private CommutePrioritized(DriverCount driverCount) {
            this.driverCount = driverCount;
        }

        @Override
        public int compareTo(CommutePrioritized other) {
            int result = driverCount.compareTo(other.driverCount);
            if(result == 0)
                return myDriverIsFirstInOrder(other) ? -1 : 1;
            return result;
        }

        private boolean myDriverIsFirstInOrder(CommutePrioritized other) {
            return driversOrdered.getFirstInOrder(driverCount.getDriver(), other.driverCount.getDriver()) == driverCount.getDriver();
        }
    }
}
