/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.distance.Distance;
import com.montealegreluis.yelpv3.businesses.distance.UnitOfLength;

import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.*;

public class Radius extends Distance {
    static final Distance largest = Distance.inMeters(40000);

    public static Radius inMeters(double meters) {
        return new Radius(meters, METERS);
    }

    public static Radius inMiles(double miles) {
        return new Radius(miles, MILES);
    }

    public static Radius inKilometers(double kilometers) {
        return new Radius(kilometers, KILOMETERS);
    }

    /**
     * Yelp's search criteria expects the distance to a business to be measured in meters
     */
    public String toMeters() {
        return Integer.valueOf(this.convertTo(METERS).value.intValue()).toString();
    }


    protected Radius(double value, UnitOfLength unit) {
        super(value, unit);
        if (largest.smallerThan(this))
            throw AreaTooLarge.withADistanceOf(value, unit);
    }
}
