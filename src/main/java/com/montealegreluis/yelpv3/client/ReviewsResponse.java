/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import com.montealegreluis.yelpv3.reviews.Review;

import java.util.List;

public class ReviewsResponse extends Response {
    public static ReviewsResponse fromOriginalResponse(String jsonResponse) {
        return new ReviewsResponse(jsonResponse);
    }

    public List<Review> reviews() {
        return parser.reviews(jsonResponse);
    }

    private ReviewsResponse(String jsonResponse) {
        super(jsonResponse);
    }
}
