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

    private SearchCriteria(String location) {
        parameters.put("location", location);
    }

    public void addQueryParametersTo(URIBuilder builder) {
        parameters.forEach(builder::setParameter);
    }
}
