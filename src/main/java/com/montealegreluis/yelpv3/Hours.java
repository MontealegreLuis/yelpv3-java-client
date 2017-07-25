/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Hours {
    private boolean overnight = false;
    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;

    public static Hours from(JSONObject jsonHours) {
        return new Hours(jsonHours);
    }

    public boolean overnight() {
        return overnight;
    }

    public DayOfWeek day() {
        return day;
    }

    public LocalTime start() {
        return start;
    }

    public LocalTime end() {
        return end;
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
