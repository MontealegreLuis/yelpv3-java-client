/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Hours {
    public final boolean isOvernight;
    public final DayOfWeek day;
    public final LocalTime start;
    public final LocalTime end;

    public Hours(DayOfWeek day, LocalTime start, LocalTime end) {
        isOvernight = false;
        this.day = day;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null) return false;
        if (!(another instanceof Hours)) return false;

        Hours anotherHours = (Hours) another;

        return isOvernight == anotherHours.isOvernight
            && day.equals(anotherHours.day)
            && start.equals(anotherHours.start)
            && end.equals(anotherHours.end)
        ;
    }
}
