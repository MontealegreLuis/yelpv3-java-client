/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class YelpTest {
    @Test
    public void it_gets_an_access_token() throws Exception {
        AccessToken token = yelp.authenticate();

        assertThat(token.tokenType(), is("Bearer"));
        assertThat(token.accessToken().length(), is(128));
        assertThat(token.isExpired(), is(false));
    }

    @Test
    public void it_searches_by_location() {
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        List<Business> businesses = yelp.search(criteria);

        assertThat(businesses.size(), is(20));
        assertThat(businesses.get(0).location().city(), is("San Antonio"));
        assertThat(businesses.get(19).location().city(), is("San Antonio"));
    }

    @Before
    public void loadYelpCredentials() throws IOException {
        Properties properties = new Properties();
        Path path = Paths.get("src/main/resources/application.properties");
        properties.load(new FileInputStream(path.toAbsolutePath().toString()));

        yelp = new Yelp(
            properties.getProperty("yelp.api.client_id"),
            properties.getProperty("yelp.api.client_secret")
        );
    }

    private Yelp yelp;
}
