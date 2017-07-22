/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {
    private Map<String, String> parameters = new HashMap<>();

    public static SearchCriteria byLocation(String location) {
        return new SearchCriteria(location);
    }

    public static SearchCriteria byCoordinates(Double latitude, Double longitude) {
        return new SearchCriteria(latitude, longitude);
    }

    public SearchCriteria limit(Integer limit) {
        if (limit > 50) {
            throw new RuntimeException(String.format(
                "Maximum amount of results is 50, %d given", limit
            ));
        }

        parameters.put("limit", limit.toString());
        return this;
    }

    public SearchCriteria offset(Integer offset) {
        parameters.put("offset", offset.toString());
        return this;
    }

    public SearchCriteria withTerm(String term) {
        parameters.put("term", term);
        return this;
    }

    public SearchCriteria withinARadiusOf(Integer meters) {
        if (meters > 40000) {
            throw new RuntimeException(String.format(
                "Cannot search within a radius greater than 40000 meters, %d given",
                meters
            ));
        }
        parameters.put("radius", meters.toString());
        return this;
    }

    public SearchCriteria onlyOpenBusinesses() {
        parameters.put("open_now", Boolean.toString(true));
        return this;
    }

    /**
     * @param categories Comma separated list of categories
     *                   See https://www.yelp.com/developers/documentation/v2/all_category_list
     */
    public SearchCriteria inCategories(String categories) {
        parameters.put("categories", categories);
        return this;
    }

    public SearchCriteria withPricing(PricingLevel level) {
        parameters.put("price", level.value().toString());
        return this;
    }

    public SearchCriteria withAttributes(Attribute... attributes) {
        StringBuilder filters = new StringBuilder();
        for (Attribute attribute : attributes) filters.append(attribute.value()).append(",");
        parameters.put("attributes", filters.substring(0, filters.length() - 1));
        return this;
    }

    public SearchCriteria openAt(Long timestamp) {
        parameters.put("open_at", timestamp.toString());
        return this;
    }

    void addQueryParametersTo(URIBuilder builder) {
        parameters.forEach(builder::setParameter);
    }

    private SearchCriteria(Double latitude, Double longitude) {
        parameters.put("latitude", latitude.toString());
        parameters.put("longitude", longitude.toString());
    }

    private SearchCriteria(String location) {
        parameters.put("location", location);
    }

    @Override
    public String toString() {
        return parameters.toString();
    }
}
