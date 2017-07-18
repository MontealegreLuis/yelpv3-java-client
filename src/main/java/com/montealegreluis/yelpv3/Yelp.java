/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Yelp {
    private final Credentials credentials;
    private final YelpClient yelpClient;

    public Yelp(Credentials credentials) {
        this(credentials, new YelpClient(HttpClientBuilder.create().build(), new YelpURIs()));
    }

    public Yelp(Credentials credentials, YelpClient yelpClient) {
        this.credentials = credentials;
        this.yelpClient = yelpClient;
    }

    public List<Business> search(SearchCriteria criteria) {
        try {
            yelpClient.allBusinessesMatching(criteria, token().accessToken());
            return parseResults(new JSONObject(yelpClient.responseBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Business searchById(String id) {
        try {
            yelpClient.businessWith(id, token().accessToken());
            return Business.from(new JSONObject(yelpClient.responseBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AccessToken token() {
        if (credentials.isTokenExpired()) authenticate();

        return credentials.token();
    }

    private void authenticate() {
        try {
            yelpClient.authenticate(credentials.toMap());
            credentials.updateToken(createAccessToken(yelpClient.responseBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Business> parseResults(JSONObject results) {
        List<Business> businesses = new ArrayList<>();
        JSONArray jsonArray = results.getJSONArray("businesses");

        for (int i = 0; i < jsonArray.length(); i++)
            businesses.add(Business.from(jsonArray.getJSONObject(i)));

        return businesses;
    }

    private AccessToken createAccessToken(String jsonResponse) throws IOException {
        JSONObject token = new JSONObject(jsonResponse);

        return AccessToken.fromYELP(
            token.getString("access_token"),
            token.getString("token_type"),
            token.getLong("expires_in")
        );
    }
}
