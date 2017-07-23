/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.montealegreluis.yelpv3.Attribute.*;
import static com.montealegreluis.yelpv3.SortingMode.REVIEW_COUNT;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class YelpTest {
    @Test
    public void it_gets_an_access_token() throws Exception {
        Yelp yelp = new Yelp(new Credentials(clientID, clientSecret));
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
    public void it_includes_a_term_in_the_search() {
        List<Business> businesses = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .withTerm("bbq")
        );
        assertThat(businesses.size(), is(2));
        assertThat(businesses.get(0).isInCategory("bbq"), is(true));
        assertThat(businesses.get(1).isInCategory("bbq"), is(true));
    }

    @Test
    public void it_searches_within_a_specific_radius() {
        int radiusInMeters = 1000;
        int deltaInMeters = 200; // Brittle warning...
        SearchCriteria criteria = SearchCriteria
            .byLocation("San Antonio")
            .withinARadiusOf(radiusInMeters)
            .limit(2)
        ;
        List<Business> businesses = yelp.search(criteria);
        assertThat(businesses.size(), is(2));
        assertThat(
            businesses.get(0).distance(),
            is(lessThan((double) (radiusInMeters + deltaInMeters)))
        );
        assertThat(
            businesses.get(1).distance(),
            is(lessThan((double) (radiusInMeters + deltaInMeters)))
        );
    }

    @Test
    public void it_searches_only_open_businesses() {
        List<Business> businesses = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .onlyOpenBusinesses()
            .limit(1)
        );
        assertThat(businesses.size(), is(1));

        Business businessDetails = yelp.searchById(businesses.get(0).id());

        assertThat(businessDetails.schedule().isOpenNow(), is(true));
    }

    @Test
    public void it_searches_by_category() {
        List<Business> restaurants = yelp.search(SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .inCategories("mexican")
            .limit(2)
        );
        assertThat(restaurants.size(), is(2));
        assertThat(restaurants.get(0).isInCategory("mexican"), is(true));
        assertThat(restaurants.get(1).isInCategory("mexican"), is(true));
    }

    @Test
    public void it_searches_with_a_specific_price_level() {
        List<Business> businesses = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .withPricing(PricingLevel.AVERAGE)
            .limit(2)
        );

        assertThat(businesses.size(), is(2));
        assertThat(businesses.get(0).priceLevel(), is(PricingLevel.AVERAGE.symbol()));
        assertThat(businesses.get(1).priceLevel(), is(PricingLevel.AVERAGE.symbol()));
    }

    @Test
    public void it_searches_with_specific_attributes() {
        SearchCriteria criteria = SearchCriteria
            .byLocation("San Antonio")
            .withAttributes(HOT_AND_NEW, DEALS)
            .limit(1)
        ;

        List<Business> businesses = yelp.search(criteria);

        assertThat(businesses.size(), is(1)); // I don't know how to confirm it, I can only trust it :p
    }

    @Test
    public void it_searches_businesses_opened_at_a_given_time() {
        List<Business> businessesOpenAt = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .openAt(Instant.now().getEpochSecond())
            .limit(1)
        );

        assertThat(businessesOpenAt.size(), is(1));

        List<Business> businessesOpenNow = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .onlyOpenBusinesses()
            .limit(1)
        );

        assertThat(businessesOpenNow.size(), is(1));

        assertThat(businessesOpenAt.get(0).id(), is(businessesOpenNow.get(0).id()));
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
    public void it_paginates_a_search_result() {
        List<Business> all = yelp.search(SearchCriteria.byLocation("San Antonio").limit(4));
        List<Business> firstTwo = yelp.search(SearchCriteria.byLocation("San Antonio").limit(2));
        List<Business> lastTwo = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .offset(2)
        );

        assertThat(all.get(0).id(), is(firstTwo.get(0).id()));
        assertThat(all.get(1).id(), is(firstTwo.get(1).id()));
        assertThat(all.get(2).id(), is(lastTwo.get(0).id()));
        assertThat(all.get(3).id(), is(lastTwo.get(1).id()));
    }

    @Test
    public void it_sorts_the_search_by_given_mode() {
        List<Business> sorted = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .sortBy(REVIEW_COUNT)
        );

        assertThat(sorted.get(0).reviewCount(), greaterThan(sorted.get(1).reviewCount()));
        assertThat(sorted.get(1).reviewCount(), greaterThan(sorted.get(2).reviewCount()));
    }

    @Test
    public void it_searches_using_a_specific_locale() throws Exception {
        List<Business> businesses = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .withLocale(new Locale("es", "MX"))
            .limit(1)
        );

        DetectLanguage.apiKey = languageDetectKey;
        List<Result> detected = new ArrayList<>();
        for (Category category : businesses.get(0).categories())
            detected.addAll(DetectLanguage.detect(category.getTitle()));

        List<Result> categoriesInSpanish = detected
            .stream()
            .filter(result -> result.isReliable && result.language.equalsIgnoreCase("es"))
            .collect(Collectors.toList())
        ;

        // Translations are not accurate, so at least 1 should be detected as Spanish
        assertThat(categoriesInSpanish.size(), greaterThan(0));
    }

    @Test
    public void it_searches_by_id() {
        String businessId = "bella-on-the-river-san-antonio";

        Business business = yelp.searchById(businessId);

        assertThat(business.id(), is(businessId));
        assertThat(business.location().city(), is("San Antonio"));
    }

    @Test
    public void it_uses_an_existing_token() {
        Yelp yelp = new Yelp(new Credentials(clientID, clientSecret, token));
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

        languageDetectKey = properties.getProperty("languagedetect.api.key");
        clientID = properties.getProperty("yelp.api.client_id");
        clientSecret = properties.getProperty("yelp.api.client_secret");

        yelp = new Yelp(new Credentials(clientID, clientSecret));
        token = yelp.token();
    }

    private static Yelp yelp;
    private static String clientID;
    private static String clientSecret;
    private static AccessToken token;
    private static String languageDetectKey;
}
