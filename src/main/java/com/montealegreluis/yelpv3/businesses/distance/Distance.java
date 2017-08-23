/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

abstract public class Distance {
    enum Unit {METERS, MILES}

    public final Unit unit;
    public final Double value;

    protected Distance(Unit unit, double value) {
        this.unit = unit;
        this.value = value;
    }

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
        return value > distance.value;
    }

    public boolean smallerThan(Distance distance) {
        return value < distance.value;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit.toString().toLowerCase());
    }

    public abstract DistanceInMeters toMeters();

    public abstract DistanceInMiles toMiles();
}
