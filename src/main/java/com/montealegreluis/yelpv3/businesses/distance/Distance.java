/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

abstract public class Distance {
    public static Distance inMeters(double meters) {
        return new DistanceInMeters(meters);
    }

    public static Distance inMiles(double miles) {
        return new DistanceInMiles(miles);
    }

    public static Distance largest() {
        return new DistanceInMeters(40000);
    }

    public boolean biggerThan(Distance distance) {
        return toMeters() > distance.toMeters();
    }

    public boolean smallerThan(Distance distance) {
        return toMeters() < distance.toMeters();
    }

    public abstract Double toMeters();
}
