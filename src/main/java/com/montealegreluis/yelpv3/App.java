package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
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
        builder.setScheme("https").setHost("api.yelp.com").setPath("/oauth2/token");
        HttpPost post = new HttpPost(builder.build());

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", properties.getProperty("yelp.api.client_id")));
        params.add(new BasicNameValuePair("client_secret", properties.getProperty("yelp.api.client_secret")));
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpClient client = HttpClientBuilder.create().build();

        CloseableHttpResponse response = client.execute(post);

        HttpEntity entity = response.getEntity();
        StatusLine line = response.getStatusLine();
        if (line.getStatusCode() != 200) {
            System.out.println("something went wrong...");
            return;
        }
        JSONObject token = new JSONObject(EntityUtils.toString(entity));

        String accessToken = token.getString("access_token");
        System.out.printf("Access token:%s%n", accessToken);
        System.out.printf("Token type:%s%n", token.getString("token_type"));
        System.out.printf("Expires in seconds:%s%n", token.getLong("expires_in"));

        // Search in San Antonio
        builder
            .setScheme("https")
            .setHost("api.yelp.com")
            .setPath("/v3/businesses/search")
            .setParameter("term", "restaurants")
            .setParameter("location", "San Antonio")
        ;
        HttpGet get = new HttpGet(builder.build());
        get.setHeader("Authorization", String.format("Bearer %s", accessToken));
        response = client.execute(get);
        entity = response.getEntity();
        line = response.getStatusLine();
        if (line.getStatusCode() != 200) {
            System.out.println("Cannot find restaurants in San Antonio...");
            return;
        }
        JSONObject restaurants = new JSONObject(EntityUtils.toString(entity));
        JSONArray businesses = restaurants.getJSONArray("businesses");
        System.out.println(businesses.length());
        JSONObject business = businesses.getJSONObject(0);
        System.out.println(business.getString("name"));
        System.out.println(business.getInt("rating"));
        System.out.println(business.getDouble("distance"));
        JSONObject location = business.getJSONObject("location");
        System.out.println(location.getString("address1"));
        System.out.println(location.getString("zip_code"));
        JSONObject coordinates = business.getJSONObject("coordinates");
        System.out.println(coordinates.getDouble("latitude"));
        System.out.println(coordinates.getDouble("longitude"));
        JSONArray transactions = business.getJSONArray("transactions");
        if (transactions.length() > 0) transactions.getString(0);
    }
}
