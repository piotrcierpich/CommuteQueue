package com.piotrek;

import com.piotrek.statistics.DrivingRegistry;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Piotrek on 2016-10-08.
 */
public class NextToDriveTests {

    @Test
    public void WhosNextTakeFirstInOrderWhenNoDrives(){
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        NextToDrive sut = new NextToDrive(driversOrdered);
        //when
        DrivingQueue drivingQueue = sut.find(drivingRegistry);
        //expected
        DrivingQueue expectedDrivers = new DrivingQueue(new Driver[]{
            new Driver("PH"), new Driver("PA"), new Driver("PC")
        });
        assertEquals(expectedDrivers, drivingQueue);
    }

    @Test
    public void WhosNextReturnsNoMileage() {
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 2);
        NextToDrive sut = new NextToDrive(driversOrdered);
        //when
        DrivingQueue drivingQueue = sut.find(drivingRegistry);
        //expected
        DrivingQueue expectedDrivers = new DrivingQueue(new Driver[]{
            new Driver("PC"), new Driver("PA"), new Driver("PH")
        });
        assertEquals(expectedDrivers, drivingQueue);
    }

    @Test
    public void WhosNextReturnsTheLeastMileage() {
        //given
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 2);
        drivingRegistry.set(new Driver("PC"), 1);
        NextToDrive sut = new NextToDrive(driversOrdered);
        //when
        DrivingQueue drivingQueue = sut.find(drivingRegistry);
        //expected
        DrivingQueue expectedDrivers = new DrivingQueue(new Driver[]{
                new Driver("PC"), new Driver("PA"), new Driver("PH")
        });
        assertEquals(expectedDrivers, drivingQueue);
    }

    @Test
    public void NextReturnsFirstInOrderWhenMileageEqual() {
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 3);
        drivingRegistry.set(new Driver("PA"), 3);
        drivingRegistry.set(new Driver("PC"), 3);
        NextToDrive sut = new NextToDrive(driversOrdered);
        //when
        DrivingQueue drivingQueue = sut.find(drivingRegistry);
        //expected
        DrivingQueue expectedDrivers = new DrivingQueue(new Driver[]{
                new Driver("PH"),new Driver("PA"),new Driver("PC"),
        });
        assertEquals(expectedDrivers, drivingQueue);
    }

    @Test
    public void NextReturnsInOrderWhenMileageEqual() {
        DriversOrdered driversOrdered = new DriversOrdered("PH", "PA", "PC");
        DrivingRegistry drivingRegistry = new DrivingRegistry();
        drivingRegistry.set(new Driver("PH"), 4);
        drivingRegistry.set(new Driver("PA"), 3);
        drivingRegistry.set(new Driver("PC"), 3);
        NextToDrive sut = new NextToDrive(driversOrdered);
        //when
        DrivingQueue drivingQueue = sut.find(drivingRegistry);
        //expected
        DrivingQueue expectedDrivers = new DrivingQueue(new Driver[]{
                new Driver("PA"),new Driver("PC"),new Driver("PH"),
        });
        assertEquals(expectedDrivers, drivingQueue);
    }
}
