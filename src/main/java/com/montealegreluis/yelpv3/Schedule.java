/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.json.JSONArray;
import org.json.JSONObject;

public class Schedule {
    private boolean openNow;

    public static Schedule from(JSONArray hours) {
        return new Schedule(hours);
    }

    public boolean isOpenNow() {
        return openNow;
    }

    private Schedule(JSONArray hours) {
        JSONObject weekSchedule = hours.getJSONObject(0);
        openNow = weekSchedule.getBoolean("is_open_now");
    }
}
