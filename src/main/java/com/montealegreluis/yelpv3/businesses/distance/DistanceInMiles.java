/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

public class DistanceInMiles extends Distance {

    public DistanceInMiles(double miles) {
        super(Unit.MILES, miles);
    }

    @Override
    public Double toMeters() {
        return value * 1609.344;
    }

    @Override
    public Double toMiles() {
        return value;
    }
}
