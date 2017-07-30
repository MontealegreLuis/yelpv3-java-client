/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

public class Region {
    public final Coordinates center;

    public static Region withCenter(Coordinates center) {
        return new Region(center);
    }

    private Region(Coordinates center) {
        this.center = center;
    }
}
