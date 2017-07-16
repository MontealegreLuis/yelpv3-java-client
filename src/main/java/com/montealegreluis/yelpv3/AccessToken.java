/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import java.util.Date;

public class AccessToken {
    private final String accessToken;
    private final String tokenType;
    private final long expiresAt;

    public AccessToken(String accessToken, String tokenType, long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresAt = new Date().getTime() + expiresIn;
    }

    public String accessToken() {
        return accessToken;
    }

    public String tokenType() {
        return tokenType;
    }

    public boolean isExpired() {
        return expiresAt < new Date().getTime();
    }
}
