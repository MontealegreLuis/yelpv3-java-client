/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Factory with links to the endpoints currently available
 */
public class YelpURIs {
    public URI searchBy(SearchCriteria criteria) {
        try {
            URIBuilder builder = yelpURIBuilder().setPath("/v3/businesses/search");
            criteria.addQueryParametersTo(builder);
            return builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URI businessBy(String id) {
        try {
            return yelpURIBuilder().setPath(String.format("/v3/businesses/%s", id)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URI reviews(String id) {
        try {
            return yelpURIBuilder().setPath(String.format("/v3/businesses/%s/reviews", id)).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URI authentication() {
        try {
            return yelpURIBuilder().setPath("/oauth2/token").build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URIBuilder yelpURIBuilder() {
        return new URIBuilder().setScheme("https").setHost("api.yelp.com");
    }
}
