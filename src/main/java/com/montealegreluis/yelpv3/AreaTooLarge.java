/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

public class AreaTooLarge extends RuntimeException {
    private AreaTooLarge(String format) {
        super(format);
    }

    public static AreaTooLarge withAMeasureOf(Integer meters) {
        return new AreaTooLarge(String.format(
            "Cannot search within a radius greater than %d meters, %d given",
            40000,
            meters
        ));
    }
}
