/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YelpClient {
    private final CloseableHttpClient client;
    private CloseableHttpResponse response;
    private final YelpURIs yelpURIs;

    public YelpClient(CloseableHttpClient client, YelpURIs yelpURIs) {
        this.client = client;
        this.yelpURIs = yelpURIs;
    }

    public void allBusinessesMatching(SearchCriteria criteria, String accessToken) throws IOException {
        getFrom(yelpURIs.searchBy(criteria), accessToken);
    }

    public void businessWith(String id, String accessToken) throws IOException {
        getFrom(yelpURIs.businessBy(id), accessToken);
    }

    public void authenticate(Map<String, String> credentials) throws IOException {
        postTo(yelpURIs.authentication(), credentials);
    }

    private void postTo(
        URI uri,
        Map<String, String> bodyParameters
    ) throws IOException {
        HttpPost post = new HttpPost(uri);
        post.setEntity(createFormEntityWith(bodyParameters));
        response = client.execute(post);
        checkStatus(uri);
    }

    private void getFrom(URI uri, String bearerToken) throws IOException {
        HttpGet get = new HttpGet(uri);
        get.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        response = client.execute(get);
        checkStatus(uri);
    }

    public String responseBody() throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private void checkStatus(URI uri) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) return;

        throw new RuntimeException(String.format(
            "HTTP Error occurred%nStatus code: %d%nURI: %s%nResponse body: %s",
            statusCode,
            uri.toString(),
            EntityUtils.toString(response.getEntity())
        ));
    }

    private UrlEncodedFormEntity createFormEntityWith(
        Map<String, String> parameters
    ) throws UnsupportedEncodingException {
        List<NameValuePair> params = new ArrayList<>();
        parameters.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
        return new UrlEncodedFormEntity(params);
    }
}
