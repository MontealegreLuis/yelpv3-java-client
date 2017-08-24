/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import com.montealegreluis.yelpv3.businesses.BusinessDetails;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.client.AccessToken;
import com.montealegreluis.yelpv3.reviews.Review;

import java.util.List;

public interface Parser {
    BusinessDetails business(String response);

    SearchResult searchResult(String response);

    List<Review> reviews(String response);

    AccessToken token(String response);
}
