/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.client.AccessToken;
import com.montealegreluis.yelpv3.businesses.Business;
import org.json.JSONObject;

public class JSONParser implements Parser {
    @Override
    public Business business(String response) {
        return BusinessParser.parseFrom(new JSONObject(response));
    }

    @Override
    public SearchResult businesses(String response) {
        return SearchResultParser.parseFrom(new JSONObject(response));
    }

    @Override
    public AccessToken token(String response) {
        JSONObject token = new JSONObject(response);

        return AccessToken.fromYELP(
            token.getString("access_token"),
            token.getString("token_type"),
            token.getLong("expires_in")
        );
    }
}
