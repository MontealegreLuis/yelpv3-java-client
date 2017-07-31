/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.montealegreluis.yelpv3.client.*;
import com.montealegreluis.yelpv3.jsonparser.JSONParser;
import com.montealegreluis.yelpv3.parser.Parser;
import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Yelp {
    private final Credentials credentials;
    private final YelpClient yelpClient;
    private final Parser parser;

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

    public Yelp(Credentials credentials, YelpClient yelpClient, Parser parser) {
        this.credentials = credentials;
        this.yelpClient = yelpClient;
        this.parser = parser;
    }

    public SearchResponse search(SearchCriteria criteria) {
        try {
            yelpClient.allBusinessesMatching(criteria, token().accessToken());
            return SearchResponse.fromOriginalResponse(yelpClient.responseBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessResponse searchById(String id) {
        try {
            yelpClient.businessWith(id, token().accessToken());
            return BusinessResponse.fromOriginalResponse(yelpClient.responseBody());
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
