/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import java.util.Date;

public class AccessToken {
    private final String accessToken;
    private final String tokenType;
    private final long expiresOn;

    private AccessToken(String accessToken, String tokenType, long expiresOn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresOn = expiresOn;
    }

    public static AccessToken fromYELP(String accessToken, String tokenType, long expiresIn) {
        return new AccessToken(
            accessToken, tokenType, new Date().getTime() + expiresIn
        );
    }

    public static AccessToken fromValues(String accessToken, String tokenType, long expiresOn) {
        return new AccessToken(accessToken, tokenType, expiresOn);
    }

    public String accessToken() {
        return accessToken;
    }

    public String tokenType() {
        return tokenType;
    }

    public long expiresOn() {
        return expiresOn;
    }

    public boolean isExpired() {
        return expiresOn < new Date().getTime();
    }
}
