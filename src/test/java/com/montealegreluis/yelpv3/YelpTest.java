/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class YelpTest {
    @Test
    public void it_gets_an_access_token() throws Exception {
        Yelp yelp = new Yelp(clientID, clientSecret);
        AccessToken token = yelp.token();

        assertThat(token.tokenType(), is("Bearer"));
        assertThat(token.accessToken().length(), is(128));
        assertThat(token.isExpired(), is(false));
    }

    @Test
    public void it_searches_by_location() {
        List<Business> businesses = yelp.search(SearchCriteria.byLocation("San Antonio"));

        assertThat(businesses.size(), is(20));
        assertThat(businesses.get(0).location().city(), is("San Antonio"));
        assertThat(businesses.get(19).location().city(), is("San Antonio"));
    }

    @Test
    public void it_searches_by_coordinates() {
        List<Business> businesses = yelp.search(SearchCriteria.byCoordinates(29.426786, -98.489576));

        assertThat(businesses.size(), greaterThan(0));
        assertThat(businesses.get(0).location().city(), is("San Antonio"));
    }

    @Test
    public void it_limits_the_amount_of_results() {
        SearchCriteria criteria = SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(3)
        ;
        List<Business> businesses = yelp.search(criteria);

        assertThat(businesses.size(), is(3));
        assertThat(businesses.get(0).location().city(), is("San Antonio"));
        assertThat(businesses.get(1).location().city(), is("San Antonio"));
        assertThat(businesses.get(2).location().city(), is("San Antonio"));
    }

    @Test
    public void it_does_not_allow_more_than_50_results() {
        exception.expect(RuntimeException.class);

        SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(3)
        ;
    }

    @Test
    public void it_searches_by_id()
    {
        String businessId = "bella-on-the-river-san-antonio";

        Business business = yelp.searchById(businessId);

        assertThat(business.id(), is(businessId));
        assertThat(business.location().city(), is("San Antonio"));
    }

    @Test
    public void it_uses_an_existing_token()
    {
        Yelp yelp = new Yelp(clientID, clientSecret, token);
        String businessId = "bella-on-the-river-san-antonio";

        Business business = yelp.searchById(businessId);

        assertThat(business.id(), is(businessId));
        assertThat(business.location().city(), is("San Antonio"));
    }

    @BeforeClass
    public static void loadYelpCredentials() throws Exception {
        Properties properties = new Properties();
        Path path = Paths.get("src/main/resources/application.properties");
        properties.load(new FileInputStream(path.toAbsolutePath().toString()));

        clientID = properties.getProperty("yelp.api.client_id");
        clientSecret = properties.getProperty("yelp.api.client_secret");

        yelp = new Yelp(clientID, clientSecret);
        token = yelp.token();
    }

    private static Yelp yelp;
    private static String clientID;
    private static String clientSecret;
    private static AccessToken token;
    private ExpectedException exception = ExpectedException.none();
}
