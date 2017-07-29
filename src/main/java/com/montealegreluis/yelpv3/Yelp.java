/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.parser.JSONParser;
import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Yelp {
    private final Credentials credentials;
    private final YelpClient yelpClient;
    private final JSONParser parser;

    public Yelp(Credentials credentials) {
        this(
            credentials,
            new YelpClient(HttpClientBuilder.create().build(), new YelpURIs()),
            new JSONParser()
        );
    }

    public Yelp(Credentials credentials, YelpClient yelpClient) {
        this(credentials, yelpClient, new JSONParser());
    }

    public Yelp(Credentials credentials, YelpClient yelpClient, JSONParser parser) {
        this.credentials = credentials;
        this.yelpClient = yelpClient;
        this.parser = parser;
    }

    public List<Business> search(SearchCriteria criteria) {
        try {
            yelpClient.allBusinessesMatching(criteria, token().accessToken());
            return parser.businesses(yelpClient.responseBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Business searchById(String id) {
        try {
            yelpClient.businessWith(id, token().accessToken());
            return parser.business(yelpClient.responseBody());
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
            credentials.updateToken(parser.token(yelpClient.responseBody()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
