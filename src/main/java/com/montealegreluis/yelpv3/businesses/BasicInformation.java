/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BasicInformation {
    public final double rating;
    public final PricingLevel pricingLevel;
    public final String phone;
    public final String id;
    public final boolean closedPermanently;
    public final List<Category> categories = new ArrayList<>();
    public final int reviewCount;
    public final String name;
    public final String url;
    public final Coordinates coordinates;
    public final String image;
    public final Location location;
    public final double distanceInMeters;
    public final List<String> transactions = new ArrayList<>();

    static BasicInformation from(JSONObject information) {
        return new BasicInformation(information);
    }

    private BasicInformation(JSONObject information) {
        rating = information.getDouble("rating");
        pricingLevel = information.has("price") ? PricingLevel.fromSymbol(information.getString("price")) : PricingLevel.NONE;
        phone = information.getString("phone");
        id = information.getString("id");
        closedPermanently = information.getBoolean("is_closed");
        setCategories(information.getJSONArray("categories"));
        reviewCount = information.getInt("review_count");
        name = information.getString("name");
        url = information.getString("url");
        coordinates = Coordinates.from(information.getJSONObject("coordinates"));
        image = information.getString("image_url");
        location = Location.from(information.getJSONObject("location"));
        distanceInMeters = !information.isNull("distance") ? information.getDouble("distance") : 0.0;
        setTransactions(information.getJSONArray("transactions"));
    }

    private void setCategories(JSONArray businessCategories) {
        for (int i = 0; i < businessCategories.length(); i++)
            categories.add(Category.from(businessCategories.getJSONObject(i)));
    }

    private void setTransactions(JSONArray transactions) {
        for (int i = 0; i < transactions.length(); i++)
            this.transactions.add(transactions.getString(i));
    }
}
