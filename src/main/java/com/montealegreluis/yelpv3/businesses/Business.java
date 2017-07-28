/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.ParsingFailure;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Business {
    public final BasicInformation basicInformation;
    public final boolean claimed;
    public final List<String> photos = new ArrayList<>();
    public final Schedule schedule;

    public static Business from(JSONObject information) {
        try {
            return new Business(information);
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(information, exception);
        }
    }

    public boolean isClaimed() {
        return claimed;
    }

    public Schedule schedule() {
        return schedule;
    }

    private Business(JSONObject information) {
        basicInformation = BasicInformation.from(information);
        claimed = !information.isNull("is_claimed") && information.getBoolean("is_claimed");
        schedule = !information.isNull("hours") ? Schedule.from(information.getJSONArray("hours")) : null;
        if (!information.isNull("photos")) addAllTo(information.getJSONArray("photos"), photos);
    }

    private void addAllTo(JSONArray entries, List<String> collection) {
        for (int i = 0; i < entries.length(); i++) collection.add(entries.getString(i));
    }

    public boolean isInCategory(String categoryAlias) {
        return basicInformation.categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }
}
