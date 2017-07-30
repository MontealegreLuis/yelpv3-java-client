/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.distance.Distance;

public class AreaTooLarge extends RuntimeException {
    private AreaTooLarge(String format) {
        super(format);
    }

    public static AreaTooLarge withADistanceOf(Distance distance) {
        return new AreaTooLarge(String.format(
            "Cannot search within a radius greater than %s, %s given",
            Distance.largest().toString(),
            distance.toString()
        ));
    }
}
