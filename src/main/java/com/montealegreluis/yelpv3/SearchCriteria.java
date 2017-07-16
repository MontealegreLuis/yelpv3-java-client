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

    @Override
    public String toString() {
        return parameters.toString();
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
}
