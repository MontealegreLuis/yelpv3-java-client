/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class BasicInformation {
    final double rating;
    final PricingLevel pricingLevel;
    final String phone;
    final String id;
    final boolean closedPermanently;
    final List<Category> categories = new ArrayList<>();
    final int reviewCount;
    final String name;
    final String url;
    final Coordinates coordinates;
    final String image;
    final Location location;
    final double distanceInMeters;
    final List<String> transactions = new ArrayList<>();

    static BasicInformation from(JSONObject information) {
        return new BasicInformation(information);
    }

    private BasicInformation(JSONObject information) {
        rating = information.getDouble("rating");
        pricingLevel = information.has("price") ? PricingLevel.fromSymbol(information.getString("price")) : null;
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
