/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

public class DistanceInMeters extends Distance {

    DistanceInMeters(double meters) {
        super(Unit.METERS, meters);
    }

    @Override
    public DistanceInMiles toMiles() {
        return new DistanceInMiles(value / 1609.344);
    }

    @Override
    public DistanceInMeters toMeters() {
        return new DistanceInMeters(value);
    }
}
