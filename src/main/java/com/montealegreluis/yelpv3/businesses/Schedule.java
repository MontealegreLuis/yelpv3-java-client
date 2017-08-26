/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A business can be opened more than once a day (morning and evening for instance)
 * <p>
 * <pre>
 * Fri 11:00-15:00hrs. AND 17:00-22:00 hrs.
 * Sat 17:00 - 22:00 hrs.
 * </pre>
 * <p>
 * Currently Yelp sets the type of hours to <code>"REGULAR"</code> always
 * <p>
 * A business' schedule includes a convenient value to know if the business is currently open
 */
public class Schedule {
    public final String hoursType;
    public final boolean isOpenNow;
    public final Map<DayOfWeek, List<Hours>> hours;

    public Schedule(boolean isOpenNow, Map<DayOfWeek, List<Hours>> hours) {
        this.hoursType = "REGULAR";
        this.isOpenNow = isOpenNow;
        this.hours = Collections.unmodifiableMap(hours);
    }

    public List<Hours> hoursFor(DayOfWeek day) {
        return this.hours
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().equals(day))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(Collections.emptyList())
        ;
    }
}
