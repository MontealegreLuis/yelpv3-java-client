/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Hours {
    public final boolean overnight;
    public final DayOfWeek day;
    public final LocalTime start;
    public final LocalTime end;

    public Hours(DayOfWeek day, LocalTime start, LocalTime end) {
        overnight = false;
        this.day = day;
        this.start = start;
        this.end = end;
    }
}
