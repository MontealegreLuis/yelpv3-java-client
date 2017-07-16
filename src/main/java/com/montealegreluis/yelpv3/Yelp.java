/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Yelp {
    private final String clientId;
    private final String clientSecret;
    private URI authenticationURI;
    private CloseableHttpClient client;

    public Yelp(String clientId, String clientSecret) {
        authenticationURI = createAuthenticationURI();
        client = HttpClientBuilder.create().build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public AccessToken authenticate() {
        try {
            CloseableHttpResponse response = requestToken();

            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Something went wrong");

            return createAccessToken(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AccessToken createAccessToken(HttpEntity json) throws IOException {
        JSONObject token = new JSONObject(EntityUtils.toString(json));

        return new AccessToken(
            token.getString("access_token"),
            token.getString("token_type"),
            token.getLong("expires_in")
        );
    }

    private CloseableHttpResponse requestToken() throws IOException {
        HttpPost post = new HttpPost(authenticationURI);
        post.setEntity(authenticationParameters());
        return client.execute(post);
    }

    private UrlEncodedFormEntity authenticationParameters() throws UnsupportedEncodingException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        return new UrlEncodedFormEntity(params);
    }

    private URI createAuthenticationURI() {
        try {
            return new URIBuilder()
                .setScheme("https")
                .setHost("api.yelp.com")
                .setPath("/oauth2/token")
                .build()
            ;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
