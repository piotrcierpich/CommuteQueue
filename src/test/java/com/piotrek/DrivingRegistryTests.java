package com.piotrek;

import com.piotrek.statistics.DriverCount;
import com.piotrek.statistics.DrivingRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Piotrek on 2017-01-31.
 */
public class DrivingRegistryTests {
    @Test
    public void DriverCountShouldSumDrivesAndExcusedDrives() {
        //given
        Driver pc = new Driver("PC");
        Driver pa = new Driver("PA");
        DrivingRegistry sut = new DrivingRegistry();
        //when
        sut.addDrive(pc);
        sut.addOutOfQueue(pc);
        sut.addDrive(pa);
        //expected
        assertEquals(0, sut.getDriverCounts(pc).compareTo(new DriverCount(pc, 2)));
        assertEquals(0, sut.getDriverCounts(pa).compareTo(new DriverCount(pa, 1)));
    }
}
