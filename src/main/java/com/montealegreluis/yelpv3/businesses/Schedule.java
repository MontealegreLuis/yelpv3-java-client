/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;

public class Schedule {
    public final String hoursType;
    public final boolean openNow;
    public final List<Hours> hours;

    public Schedule(boolean openNow, List<Hours> hours) {
        this.hoursType = "REGULAR";
        this.openNow = openNow;
        this.hours = hours;
    }
}
