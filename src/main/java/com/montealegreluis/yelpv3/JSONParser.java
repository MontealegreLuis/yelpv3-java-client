/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public Business business(String response) {
        return Business.from(new JSONObject(response));
    }

    public List<Business> businesses(String response) {
        List<Business> businesses = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(response).getJSONArray("businesses");

        for (int i = 0; i < jsonArray.length(); i++)
            businesses.add(Business.from(jsonArray.getJSONObject(i)));

        return businesses;
    }

    public AccessToken token(String response) {
        JSONObject token = new JSONObject(response);

        return AccessToken.fromYELP(
            token.getString("access_token"),
            token.getString("token_type"),
            token.getLong("expires_in")
        );
    }
}
