/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import java.util.HashMap;
import java.util.Map;

/**
 * Yelp's authentication process will require you to provide a client ID and a client secret
 * in order to get a bearer access token
 * <p>
 * The access token will be required by all the API endpoints. It is recommended to save
 * this token since it will last 180 days
 */
public class Credentials {
    private final String clientId;
    private final String clientSecret;
    private AccessToken token;

    /**
     * Use this constructor if you don't have a valid access token yet
     */
    public Credentials(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Use this constructor if you have a non-expired token that you can reuse
     */
    public Credentials(String clientId, String clientSecret, AccessToken token) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.token = token;
    }

    public boolean isTokenExpired() {
        return !hasToken() || token.isExpired();
    }

    public boolean hasToken() {
        return token != null;
    }

    /**
     * Do not replace the current token unless it is expired
     */
    public void updateToken(AccessToken accessToken) {
        token = accessToken;
    }

    public AccessToken token() {
        return token;
    }

    /**
     * Convenient method to be used by the HTTP client to send the credential values as headers
     */
    public Map<String, String> toMap() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        return parameters;
    }
}
