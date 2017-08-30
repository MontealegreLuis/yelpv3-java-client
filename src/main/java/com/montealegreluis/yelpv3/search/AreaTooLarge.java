/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.distance.UnitOfLength;

/**
 * Exception thrown when searching businesses within a radius
 * <p>
 * The maximum value for searching withing a radius is 40000 meters/40 kilometers/25 miles
 */
public class AreaTooLarge extends RuntimeException {
    private AreaTooLarge(String format) {
        super(format);
    }

    public static AreaTooLarge withADistanceOf(double value, UnitOfLength unit) {
        return new AreaTooLarge(String.format(
            "Cannot search within a radius greater than %s, %.2f %s given",
            Radius.largest.toString(),
            value,
            unit.toString().toLowerCase()
        ));
    }
}
