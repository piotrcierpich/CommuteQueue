package com.piotrek;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

/**
 * Created by Piotrek on 2016-10-08.
 */
public class DriverQueueTests {

    @Test
    public void WhosNextTakeFirstInOrderWhenNoDrives(){
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        DriverQueue sut = new DriverQueue(driversOrdered);
        //when
        DriversByRegistry nextToDrive = sut.next(drivingRegistry);
        //expected
        DriversByRegistry expectedDrivers = new DriversByRegistry(new Driver[]{
            new Driver("PH"), new Driver("PA"), new Driver("PC")
        });
        assertEquals(expectedDrivers, nextToDrive);
    }

    @Test
    public void WhosNextReturnsNoMileage() {
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 2);
        DriverQueue sut = new DriverQueue(driversOrdered);
        //when
        DriversByRegistry nextToDrive = sut.next(drivingRegistry);
        //expected
        DriversByRegistry expectedDrivers = new DriversByRegistry(new Driver[]{
            new Driver("PC"), new Driver("PA"), new Driver("PH")
        });
        assertEquals(expectedDrivers, nextToDrive);
    }

    @Test
    public void WhosNextReturnsTheLeastMileage() {
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 2);
        drivingRegistry.set(new Driver("PC"), 1);
        DriverQueue sut = new DriverQueue(driversOrdered);
        //when
        DriversByRegistry nextToDrive = sut.next(drivingRegistry);
        //expected
        DriversByRegistry expectedDrivers = new DriversByRegistry(new Driver[]{
                new Driver("PC"), new Driver("PA"), new Driver("PH")
        });
        assertEquals(expectedDrivers, nextToDrive);
    }

    @Test
    public void NextReturnsFirstInOrderWhenMileageEqual() {
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 3);
        drivingRegistry.set(new Driver("PC"), 3);
        DriverQueue sut = new DriverQueue(driversOrdered);
        //when
        DriversByRegistry nextToDrive = sut.next(drivingRegistry);
        //expected
        DriversByRegistry expectedDrivers = new DriversByRegistry(new Driver[]{
                new Driver("PH"),new Driver("PA"),new Driver("PC"),
        });
        assertEquals(expectedDrivers, nextToDrive);
    }

    @Test
    public void NextReturnsInOrderWhenMileageEqual() {
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 4);
        drivingRegistry.set(new Driver("PA"), 3);
        drivingRegistry.set(new Driver("PC"), 3);
        DriverQueue sut = new DriverQueue(driversOrdered);
        //when
        DriversByRegistry nextToDrive = sut.next(drivingRegistry);
        //expected
        DriversByRegistry expectedDrivers = new DriversByRegistry(new Driver[]{
                new Driver("PA"),new Driver("PC"),new Driver("PH"),
        });
        assertEquals(expectedDrivers, nextToDrive);
    }
}
