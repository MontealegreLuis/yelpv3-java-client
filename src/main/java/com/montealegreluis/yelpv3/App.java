package com.montealegreluis.yelpv3;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class App
{
    public static void main( String[] args ) throws IOException, URISyntaxException {
        Properties properties = new Properties();
        Path path = Paths.get("src/main/resources/application.properties");
        properties.load(new FileInputStream(path.toAbsolutePath().toString()));

        Yelp yelp = new Yelp(
            properties.getProperty("yelp.api.client_id"),
            properties.getProperty("yelp.api.client_secret")
        );
        AccessToken token = yelp.authenticate();

        System.out.printf("Access token:%s%n", token.accessToken());
        System.out.printf("Token type:%s%n", token.tokenType());
        System.out.printf("Is it expired? %s%n", token.isExpired());

        // Search restaurants in San Antonio
        CloseableHttpClient client = HttpClientBuilder.create().build();

        URIBuilder builder = new URIBuilder();
        builder
            .setScheme("https")
            .setHost("api.yelp.com")
            .setPath("/v3/businesses/search")
            .setParameter("term", "restaurants")
            .setParameter("location", "San Antonio")
        ;
        HttpGet get = new HttpGet(builder.build());
        get.setHeader("Authorization", String.format("Bearer %s", token.accessToken()));

        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        StatusLine line = response.getStatusLine();
        if (line.getStatusCode() != 200) {
            System.out.println("Cannot find restaurants in San Antonio...");
            return;
        }

        JSONObject restaurants = new JSONObject(EntityUtils.toString(entity));
        JSONArray businesses = restaurants.getJSONArray("businesses");
        JSONObject business = businesses.getJSONObject(0);
        JSONObject location = business.getJSONObject("location");
        JSONObject coordinates = business.getJSONObject("coordinates");
        JSONArray transactions = business.getJSONArray("transactions");

        System.out.println(businesses.length());
        System.out.println(business.getString("name"));
        System.out.println(business.getInt("rating"));
        System.out.println(business.getDouble("distance"));
        System.out.println(location.getString("address1"));
        System.out.println(location.getString("zip_code"));
        System.out.println(coordinates.getDouble("latitude"));
        System.out.println(coordinates.getDouble("longitude"));

        if (transactions.length() > 0) transactions.getString(0);
    }
}
