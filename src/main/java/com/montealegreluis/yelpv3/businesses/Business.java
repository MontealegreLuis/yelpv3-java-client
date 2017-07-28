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
    private final BasicInformation basicInformation;
    private final boolean claimed;
    private final List<String> photos = new ArrayList<>();
    private Schedule schedule;

    public static Business from(JSONObject information) {
        try {
            return new Business(information);
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(information, exception);
        }
    }

    public String id() {
        return basicInformation.id;
    }

    public String name() {
        return basicInformation.name;
    }

    public String phone() {
        return basicInformation.phone;
    }

    public String url() {
        return basicInformation.url;
    }

    public String image() {
        return basicInformation.image;
    }

    /**
     * @return Distance in meters
     */
    public double distance() {
        return basicInformation.distanceInMeters;
    }

    public double rating() {
        return basicInformation.rating;
    }

    public int reviewCount() {
        return basicInformation.reviewCount;
    }

    public PricingLevel priceLevel() {
        return basicInformation.pricingLevel;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public boolean isClosedPermanently() {
        return basicInformation.closedPermanently;
    }

    public Location location() {
        return basicInformation.location;
    }

    public Coordinates coordinates() {
        return basicInformation.coordinates;
    }

    public Schedule schedule() {
        return schedule;
    }

    public List<Category> categories() {
        return basicInformation.categories;
    }

    public List<String> transactions() {
        return basicInformation.transactions;
    }

    public List<String> photos() {
        return photos;
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
