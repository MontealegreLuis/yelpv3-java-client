/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

public class DistanceInMiles extends Distance {

    DistanceInMiles(double miles) {
        super(Unit.MILES, miles);
    }

    @Override
    public DistanceInMeters toMeters() {
        return new DistanceInMeters(value * 1609.344);
    }

    @Override
    public DistanceInMiles toMiles() {
        return new DistanceInMiles(value);
    }
}
