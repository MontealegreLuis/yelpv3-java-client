/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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
    private final URI authenticationURI;
    private final CloseableHttpClient client;
    private AccessToken token;

    public Yelp(String clientId, String clientSecret) {
        authenticationURI = createAuthenticationURI();
        client = HttpClientBuilder.create().build();
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
        try {
            CloseableHttpResponse response = searchBusinesses(criteria);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(String.format(
                    "Cannot find businesses with criteria: %s%nSee response for more details%n%s",
                    criteria.toString(),
                    response.getEntity().getContent()
                ));
            }

            return parseResults(new JSONObject(EntityUtils.toString(response.getEntity())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Business searchById(String id) {
        try {
            CloseableHttpResponse response = searchBusiness(id);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(String.format(
                    "Cannot find businesses with id: %s%nSee response for more details%n%s",
                    id,
                    response.getEntity().getContent()
                ));
            }

            return Business.from(new JSONObject(EntityUtils.toString(response.getEntity())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CloseableHttpResponse searchBusiness(String id) throws IOException {
        HttpGet get = new HttpGet(businessURI(id));
        get.setHeader("Authorization", String.format("Bearer %s", accessToken()));
        return client.execute(get);
    }

    private URI businessURI(String id) {
        try {
            return new URIBuilder()
                .setScheme("https")
                .setHost("api.yelp.com")
                .setPath(String.format("/v3/businesses/%s", id))
                .build()
            ;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private CloseableHttpResponse searchBusinesses(SearchCriteria criteria) throws IOException {
        HttpGet get = new HttpGet(buildSearchURI(criteria));
        get.setHeader("Authorization", String.format("Bearer %s", accessToken()));
        return client.execute(get);
    }

    private URI buildSearchURI(SearchCriteria criteria) {
        try {
            URIBuilder builder = new URIBuilder();
            builder
                .setScheme("https")
                .setHost("api.yelp.com")
                .setPath("/v3/businesses/search")
            ;
            criteria.addQueryParametersTo(builder);
            return builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String accessToken() {
        if (token == null || token.isExpired()) authenticate();

        return token.accessToken();
    }

    private void authenticate() {
        try {
            CloseableHttpResponse response = requestToken();

            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Something went wrong");

            token = createAccessToken(response.getEntity());
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

    private AccessToken createAccessToken(HttpEntity json) throws IOException {
        JSONObject token = new JSONObject(EntityUtils.toString(json));

        return AccessToken.fromYELP(
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
