package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App
{
    public static void main( String[] args ) throws IOException, URISyntaxException {
        Properties properties = new Properties();
        Path path = Paths.get("src/main/resources/application.properties");
        properties.load(new FileInputStream(path.toAbsolutePath().toString()));

        URIBuilder builder = new URIBuilder();
        builder
            .setScheme("https")
            .setHost("api.yelp.com")
            .setPath("/oauth2/token")
        ;
        HttpPost post = new HttpPost(builder.build());

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", properties.getProperty("yelp.api.client_id")));
        params.add(new BasicNameValuePair("client_secret", properties.getProperty("yelp.api.client_secret")));
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(post);

        HttpEntity entity = response.getEntity();
        StatusLine line = response.getStatusLine();
        if (line.getStatusCode() != 200) {
            System.out.println("something went wrong...");
            return;
        }
        JSONObject token = new JSONObject(EntityUtils.toString(entity));

        System.out.printf("Access token:%s%n", token.getString("access_token"));
        System.out.printf("Token type:%s%n", token.getString("token_type"));
        System.out.printf("Expires in seconds:%s%n", token.getLong("expires_in"));
    }
}
