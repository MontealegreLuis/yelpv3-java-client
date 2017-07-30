/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

public class DistanceInMiles extends Distance {
    public final double miles;

    public DistanceInMiles(double miles) {
        this.miles = miles;
    }

    @Override
    public Double toMeters() {
        return miles * 1609.344;
    }

    @Override
    public String toString() {
        return String.format("%.2f miles", miles);
    }
}
