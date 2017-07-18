/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import java.util.HashMap;
import java.util.Map;

public class Credentials {
    private final String clientId;
    private final String clientSecret;
    private AccessToken token;

    public Credentials(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Credentials(String clientId, String clientSecret, AccessToken token) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.token = token;
    }

    public boolean hasToken() {
        return token != null;
    }

    public boolean isTokenExpired() {
        return !hasToken() || token.isExpired();
    }

    public void updateToken(AccessToken accessToken) {
        token = accessToken;
    }

    public AccessToken token() {
        return token;
    }

    public Map<String, String> toMap() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        return parameters;
    }
}
