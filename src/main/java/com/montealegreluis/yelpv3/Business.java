/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Business {
    private final String id;
    private final String name;
    private final String phone;
    private final String url;
    private final String image;
    private final double distanceInMeters;
    private final double rating;
    private final int reviewCount;
    private final String priceLevel;
    private final Location location;
    private final Coordinates coordinates;
    private final List<String> transactions = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public static Business from(JSONObject information) {
        try {
            return new Business(information);
        } catch (JSONException e) {
            throw new RuntimeException(
                String.format("Cannot parse object%s", information.toString(2)),
                e
            );
        }
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String phone() {
        return phone;
    }

    public String url() {
        return url;
    }

    public String image() {
        return image;
    }

    /**
     * @return Distance in meters
     */
    public double distance() {
        return distanceInMeters;
    }

    public double rating() {
        return rating;
    }

    public int reviewCount() {
        return reviewCount;
    }

    public String priceLevel() {
        return priceLevel;
    }

    public Location location() {
        return location;
    }

    public Coordinates coordinates() {
        return coordinates;
    }

    public List<Category> categories() {
        return categories;
    }

    public List<String> transactions() {
        return transactions;
    }

    private Business(JSONObject information) {
        id = information.getString("id");
        name = information.getString("name");
        phone = information.getString("phone");
        url = information.getString("url");
        image = information.getString("image_url");
        distanceInMeters = !information.isNull("distance") ? information.getDouble("distance") : 0.0;
        rating = information.getDouble("rating");
        reviewCount = information.getInt("review_count");
        priceLevel = information.has("price") ? information.getString("price") : "";
        location = Location.from(information.getJSONObject("location"));
        coordinates = Coordinates.from(information.getJSONObject("coordinates"));
        setCategories(information.getJSONArray("categories"));
        setTransactions(information.getJSONArray("transactions"));
    }

    private void setTransactions(JSONArray businessTransactions) {
        for (int i = 0; i < businessTransactions.length(); i++)
            transactions.add(businessTransactions.getString(i));
    }

    private void setCategories(JSONArray businessCategories) {
        for (int i = 0; i < businessCategories.length(); i++) {
            JSONObject category = businessCategories.getJSONObject(i);
            categories.add(
                new Category(category.getString("alias"), category.getString("title"))
            );
        }
    }
}
