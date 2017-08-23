/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
