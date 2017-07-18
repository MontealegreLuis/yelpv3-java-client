/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Yelp {
    private final String clientId;
    private final String clientSecret;
    private final YelpClient yelpClient;
    private final YelpURIs yelpURIs;
    private AccessToken token;

    public Yelp(String clientId, String clientSecret) {
        yelpClient = new YelpClient(HttpClientBuilder.create().build());
        yelpURIs = new YelpURIs();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Yelp(String clientID, String clientSecret, AccessToken token) {
        this(clientID, clientSecret);
        this.token = token;
    }

    public AccessToken token() {
        if (token == null) authenticate();

        return token;
    }

    public List<Business> search(SearchCriteria criteria) {
        CloseableHttpResponse response;
        try {
            response = yelpClient.getFrom(yelpURIs.searchBy(criteria), accessToken());
            return parseResults(new JSONObject(EntityUtils.toString(response.getEntity())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Business searchById(String id) {
        CloseableHttpResponse response;
        try {
            response = yelpClient.getFrom(yelpURIs.businessBy(id), accessToken());
            return Business.from(new JSONObject(EntityUtils.toString(response.getEntity())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticate() {
        CloseableHttpResponse response;
        try {
            response = yelpClient.postTo(yelpURIs.authentication(), authenticationParameters());
            token = createAccessToken(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String accessToken() {
        if (token == null || token.isExpired()) authenticate();

        return token.accessToken();
    }

    private List<Business> parseResults(JSONObject results) {
        List<Business> businesses = new ArrayList<>();
        JSONArray jsonArray = results.getJSONArray("businesses");

        for (int i = 0; i < jsonArray.length(); i++)
            businesses.add(Business.from(jsonArray.getJSONObject(i)));

        return businesses;
    }

    private AccessToken createAccessToken(HttpEntity json) throws IOException {
        JSONObject token = new JSONObject(EntityUtils.toString(json));

        return AccessToken.fromYELP(
            token.getString("access_token"),
            token.getString("token_type"),
            token.getLong("expires_in")
        );
    }

    private Map<String, String> authenticationParameters() throws UnsupportedEncodingException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        return parameters;
    }
}
