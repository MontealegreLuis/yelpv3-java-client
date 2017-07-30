/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

public class DistanceInMeters extends Distance {
    public final double meters;

    public DistanceInMeters(double meters) {
        this.meters = meters;
    }

    @Override
    public Double toMeters() {
        return meters;
    }

    @Override
    public String toString() {
        return String.format("%.2f meters", meters);
    }
}
