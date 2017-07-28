/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Details {
    public final boolean claimed;
    public final List<String> photos = new ArrayList<>();
    public final Schedule schedule;

    public static Details from(JSONObject information) {
        return new Details(information);
    }

    private Details(JSONObject information) {
        claimed = !information.isNull("is_claimed") && information.getBoolean("is_claimed");
        schedule = !information.isNull("hours") ? Schedule.from(information.getJSONArray("hours")) : null;
        if (!information.isNull("photos")) setPhotos(information.getJSONArray("photos"));
    }

    private void setPhotos(JSONArray photos) {
        for (int i = 0; i < photos.length(); i++) this.photos.add(photos.getString(i));
    }
}
