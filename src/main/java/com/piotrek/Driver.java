package com.piotrek;

/**
 * Created by Piotrek on 2016-10-08.
 */
public class Driver {
    private final String name;

    public Driver(String name) {
//        StringUtils
        if (name == null || name == "")
            throw new IllegalArgumentException("name cannot be null or empty");
        this.name = name;
    }

    @Override
    public boolean equals
            (Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Driver driver = (Driver) o;
        return name.equalsIgnoreCase(driver.name);

    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Driver " + name;
    }
}
