/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

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

    public YelpClient(CloseableHttpClient client) {
        this.client = client;
    }

    public void postTo(
        URI uri,
        Map<String, String> bodyParameters
    ) throws IOException {
        HttpPost post = new HttpPost(uri);
        post.setEntity(createFormEntityWith(bodyParameters));
        response = client.execute(post);
        checkStatus(uri);
    }

    public void getFrom(URI uri, String bearerToken) throws IOException {
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
            response.getEntity().getContent()
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
