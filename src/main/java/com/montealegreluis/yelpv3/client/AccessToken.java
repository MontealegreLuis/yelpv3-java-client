/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import java.time.Instant;

/**
 * All endpoints of the Yelp Fusion API will need a bearer access token
 * <p>
 * Currently all tokens expire after 180 days
 */
public class AccessToken {
    private final String accessToken;
    private final String tokenType;
    private final long expiresOn;

    private AccessToken(String accessToken, String tokenType, long expiresOn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresOn = expiresOn;
    }

    /**
     * This named constructor is used by the <code>JSONParser</code> class after retrieving
     * the token for the first time from Yelp
     * <p>
     * It will add the unix timestamp for the current date to the expiration time in seconds
     * given by Yelp (180 days) so that we can store the timestamp in which the token will be
     * expired
     */
    public static AccessToken fromYELP(String accessToken, String tokenType, long expiresIn) {
        return new AccessToken(
            accessToken,
            tokenType,
            Instant.now().getEpochSecond() + expiresIn
        );
    }

    /**
     * Use this named constructor when you retrieve the token from your database, ie an existing
     * token
     * <p>
     * Do not use this when you're creating a new token from Yelp. This is for
     * <strong>existing</strong> tokens
     * <p>
     * This constructor is expecting an <strong>expiration date</strong>, as a timestamp, not
     * the amount of seconds before expiring, which is the value given by Yelp
     */
    public static AccessToken fromValues(String accessToken, String tokenType, long expiresOn) {
        return new AccessToken(accessToken, tokenType, expiresOn);
    }

    public String accessToken() {
        return accessToken;
    }

    /**
     * Yelp always grant <code>bearer</code> tokens
     */
    public String tokenType() {
        return tokenType;
    }

    /**
     * Unix timestamp in which this token expires
     */
    public long expiresOn() {
        return expiresOn;
    }

    public boolean isExpired() {
        return expiresOn < Instant.now().getEpochSecond();
    }
}
