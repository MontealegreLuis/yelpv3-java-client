/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.apacheclient;

import com.montealegreluis.yelpv3.client.ErrorResponse;
import com.montealegreluis.yelpv3.client.YelpClient;
import com.montealegreluis.yelpv3.client.YelpURIs;
import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApacheHttpClient implements YelpClient {
    private final CloseableHttpClient client;
    private CloseableHttpResponse response;
    private final YelpURIs yelpURIs;

    public ApacheHttpClient(CloseableHttpClient client, YelpURIs yelpURIs) {
        this.client = client;
        this.yelpURIs = yelpURIs;
    }

    @Override
    public void allBusinessesMatching(SearchCriteria criteria, String accessToken) {
        getFrom(yelpURIs.searchBy(criteria), accessToken);
    }

    @Override
    public void businessWith(String id, String accessToken) {
        getFrom(yelpURIs.businessBy(id), accessToken);
    }

    @Override
    public void allReviewsFor(String businessId, String accessToken) {
        getFrom(yelpURIs.reviews(businessId), accessToken);
    }

    @Override
    public void authenticate(Map<String, String> credentials) {
        postTo(yelpURIs.authentication(), credentials);
    }

    @Override
    public String responseBody() {
        return parseResponseBody();
    }

    private void postTo(URI uri, Map<String, String> bodyParameters) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(createFormEntityWith(bodyParameters));
        response = executeRequest(post);
        checkStatus(uri);
    }

    private void getFrom(URI uri, String bearerToken) {
        HttpGet get = new HttpGet(uri);
        get.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        response = executeRequest(get);
        checkStatus(uri);
    }

    private CloseableHttpResponse executeRequest(HttpUriRequest request) {
        try {
            return client.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkStatus(URI uri) {
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) return;

        throw new ErrorResponse(statusCode, uri.toString(), parseResponseBody());
    }

    private String parseResponseBody() {
        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UrlEncodedFormEntity createFormEntityWith(Map<String, String> parameters) {
        try {
            List<NameValuePair> params = new ArrayList<>();
            parameters.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
            return new UrlEncodedFormEntity(params);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
