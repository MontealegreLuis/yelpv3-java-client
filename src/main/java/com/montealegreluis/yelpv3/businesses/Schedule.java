/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;

public class Schedule {
    public final String hoursType;
    public final boolean isOpenNow;
    public final List<Hours> hours;

    public Schedule(boolean isOpenNow, List<Hours> hours) {
        this.hoursType = "REGULAR";
        this.isOpenNow = isOpenNow;
        this.hours = hours;
    }
}
