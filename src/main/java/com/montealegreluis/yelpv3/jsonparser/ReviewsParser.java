/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.reviews.Review;
import com.montealegreluis.yelpv3.reviews.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReviewsParser {
    public static List<Review> parseFrom(JSONObject reviewsResult) {
        JSONArray jsonReviews = reviewsResult.getJSONArray("reviews");
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < jsonReviews.length(); i++)
            reviews.add(parseReview(jsonReviews.getJSONObject(i)));

        return reviews;
    }

    private static Review parseReview(JSONObject jsonReview) {
        JSONObject jsonUser = jsonReview.getJSONObject("user");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return new Review(
                jsonReview.getInt("rating"),
                new User(new URL(jsonUser.getString("image_url")), jsonUser.getString("name")),
                jsonReview.getString("text"),
                format.parse(jsonReview.getString("time_created")),
                new URL(jsonReview.getString("url"))
            );
        } catch (MalformedURLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
