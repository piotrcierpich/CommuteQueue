package com.piotrek;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Piotrek on 2016-10-08.
 */
public class DriversOrdered implements Iterable<Driver> {
    private final Iterable<Driver> drivers;

    public DriversOrdered(Iterable<Driver> drivers) {
        if(drivers == null)
            throw new IllegalArgumentException("drivers cannot be null");

        this.drivers = drivers;
    }

    public DriversOrdered(String... driverNames){
        if(driverNames == null)
            throw new IllegalArgumentException("drivers cannot be null");

        drivers = Arrays.asList(driverNames)
                        .stream()
                        .map(dn -> new Driver(dn))
                        .collect(Collectors.toList());
    }

    @Override
    public Iterator<Driver> iterator() {
        return drivers.iterator();
    }

    public Driver getFirstInOrder(Driver d1, Driver d2){
        for (Driver d : drivers){
            if(d.equals(d1))
                return d1;
            if(d.equals(d2))
                return d2;
        }
        throw new IllegalArgumentException("no drivers " + d1.toString() + " or " + d2.toString() + "found");
    }
}
