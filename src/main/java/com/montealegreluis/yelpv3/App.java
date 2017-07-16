/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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


        // Search businesses in San Antonio
        List<Business> businesses = yelp.search(SearchCriteria.byLocation("San Antonio"));

        System.out.println(businesses.size());
        for (Business business : businesses) printBusinessInformation(business);
    }

    private static void printBusinessInformation(Business business) {
        System.out.println("--------------------------------------");
        System.out.println(business.name());
        System.out.println(business.rating());
        System.out.println(business.distance());
        System.out.println("Location:");
        System.out.println(business.location().address1());
        System.out.println(business.location().zipCode());
        System.out.println("Coordinates:");
        System.out.println(business.coordinates().latitude());
        System.out.println(business.coordinates().longitude());
        System.out.println("Transactions");
        if (business.transactions().size() > 0)
            business.transactions().forEach(System.out::println);
        else System.out.println("No transactions for this business");
    }
}
