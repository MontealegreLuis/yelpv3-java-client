/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.Region;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class SearchResultParser {
    static SearchResult parseFrom(JSONObject result) {
        return new SearchResult(
            result.getInt("total"),
            parseBusinesses(result.getJSONArray("businesses")),
            Region.withCenter(CoordinatesParser.from(result.getJSONObject("region")))
        );
    }

    private static List<Business> parseBusinesses(JSONArray businessResults) {
        List<Business> businesses = new ArrayList<>();
        for (int i = 0; i < businessResults.length(); i++)
            businesses.add(BusinessParser.parseFrom(businessResults.getJSONObject(i)));
        return businesses;
    }
}
