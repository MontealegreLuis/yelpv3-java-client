/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    public final int total;
    public final List<Business> businesses = new ArrayList<>();
    public final Region region;

    private SearchResult(JSONObject searchResult) {
        total = searchResult.getInt("total");
        setBusinesses(searchResult);
        region = Region.withCenter(Coordinates.from(searchResult.getJSONObject("region")));
    }

    private void setBusinesses(JSONObject searchResult) {
        JSONArray businesses = searchResult.getJSONArray("businesses");
        for (int i = 0; i < businesses.length(); i++)
            this.businesses.add(Business.from(businesses.getJSONObject(i)));
    }

    public static SearchResult from(JSONObject searchResult) {
        return new SearchResult(searchResult);
    }
}
