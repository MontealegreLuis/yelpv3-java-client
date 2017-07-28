/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Hours {
    public final boolean overnight = false;
    public final DayOfWeek day;
    public final LocalTime start;
    public final LocalTime end;

    public static Hours from(JSONObject jsonHours) {
        return new Hours(jsonHours);
    }

    private Hours(JSONObject jsonHours) {
        this.start = createTimeFrom(jsonHours.getString("start"));
        this.end = createTimeFrom(jsonHours.getString("end"));
        day = DayOfWeek.of(jsonHours.getInt("day") + 1);
    }

    private LocalTime createTimeFrom(String text) {
        return LocalTime.of(
            Integer.valueOf(text.substring(0, 2)),
            Integer.valueOf(text.substring(2, 4))
        );
    }
}
