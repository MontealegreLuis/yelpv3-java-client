/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    public final String hoursType = "REGULAR";
    public final boolean openNow;
    public final List<Hours> hours = new ArrayList<>();

    public static Schedule from(JSONArray hours) {
        return new Schedule(hours);
    }

    private Schedule(JSONArray hours) {
        JSONObject weekSchedule = hours.getJSONObject(0);
        openNow = weekSchedule.getBoolean("is_open_now");
        setHours(weekSchedule.getJSONArray("open"));
    }

    private void setHours(JSONArray hours) {
        for (int i = 0; i < hours.length(); i++) this.hours.add(Hours.from(hours.getJSONObject(i)));
    }
}
