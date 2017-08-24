/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.Businesses;
import com.montealegreluis.yelpv3.businesses.Region;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.parser.ParsingFailure;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

class SearchResultParser {
    static SearchResult parseFrom(JSONObject result) {
        try {
            return new SearchResult(
                result.getInt("total"),
                parseBusinesses(result.getJSONArray("businesses")),
                Region.withCenter(CoordinatesParser.from(
                    result.getJSONObject("region").getJSONObject("center")
                ))
            );
        } catch (MalformedURLException exception) {
            throw ParsingFailure.producedBy(result, exception);
        }
    }

    private static Businesses parseBusinesses(
        JSONArray businessResults
    ) throws MalformedURLException {
        List<Business> businesses = new ArrayList<>();
        for (int i = 0; i < businessResults.length(); i++)
            businesses.add(BusinessParser.businessFrom(businessResults.getJSONObject(i)));
        return new Businesses(businesses);
    }
}
