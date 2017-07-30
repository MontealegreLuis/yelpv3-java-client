/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.businesses.Category;
import com.montealegreluis.yelpv3.businesses.PricingLevel;
import com.montealegreluis.yelpv3.client.AccessToken;
import com.montealegreluis.yelpv3.client.Credentials;
import com.montealegreluis.yelpv3.search.SearchCriteria;
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

import static com.montealegreluis.yelpv3.search.Attribute.*;
import static com.montealegreluis.yelpv3.search.SortingMode.REVIEW_COUNT;
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
        SearchResult searchResult = yelp.search(SearchCriteria.byLocation("San Antonio"));

        assertThat(searchResult.businesses.size(), is(20));
        assertThat(searchResult.businesses.get(0).basicInformation.location.city, is("San Antonio"));
        assertThat(searchResult.businesses.get(19).basicInformation.location.city, is("San Antonio"));
    }

    @Test
    public void it_includes_a_term_in_the_search() {
        SearchResult searchResult = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .withTerm("bbq")
        );
        assertThat(searchResult.businesses.size(), is(2));
        assertThat(searchResult.businesses.get(0).isInCategory("bbq"), is(true));
        assertThat(searchResult.businesses.get(1).isInCategory("bbq"), is(true));
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
        SearchResult searchResult = yelp.search(criteria);
        assertThat(searchResult.businesses.size(), is(2));
        assertThat(
            searchResult.businesses.get(0).basicInformation.distance.meters,
            is(lessThan((double) (radiusInMeters + deltaInMeters)))
        );
        assertThat(
            searchResult.businesses.get(1).basicInformation.distance.meters,
            is(lessThan((double) (radiusInMeters + deltaInMeters)))
        );
    }

    @Test
    public void it_searches_only_open_businesses() {
        SearchResult searchResult = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .onlyOpenBusinesses()
            .limit(1)
        );
        assertThat(searchResult.businesses.size(), is(1));

        Business businessDetails = yelp.searchById(searchResult.businesses.get(0).basicInformation.id);

        assertThat(businessDetails.details.schedule.isOpenNow, is(true));
    }

    @Test
    public void it_searches_by_category() {
        SearchResult restaurants = yelp.search(SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .inCategories("mexican")
            .limit(2)
        );
        assertThat(restaurants.businesses.size(), is(2));
        assertThat(restaurants.businesses.get(0).isInCategory("mexican"), is(true));
        assertThat(restaurants.businesses.get(1).isInCategory("mexican"), is(true));
    }

    @Test
    public void it_searches_with_a_specific_price_level() {
        SearchResult searchResult = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .withPricing(PricingLevel.MODERATE)
            .limit(2)
        );

        assertThat(searchResult.businesses.size(), is(2));
        assertThat(searchResult.businesses.get(0).basicInformation.pricingLevel, is(PricingLevel.MODERATE));
        assertThat(searchResult.businesses.get(1).basicInformation.pricingLevel, is(PricingLevel.MODERATE));
    }

    @Test
    public void it_searches_with_specific_attributes() {
        SearchCriteria criteria = SearchCriteria
            .byLocation("San Antonio")
            .withAttributes(HOT_AND_NEW, DEALS)
            .limit(1)
        ;

        SearchResult searchResult = yelp.search(criteria);

        assertThat(searchResult.businesses.size(), is(1)); // I don't know how to confirm it, I can only trust it :p
    }

    @Test
    public void it_searches_businesses_opened_at_a_given_time() {
        SearchResult searchResultOpenAt = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .openAt(Instant.now().getEpochSecond())
            .limit(1)
        );

        assertThat(searchResultOpenAt.businesses.size(), is(1));

        SearchResult searchResultOpenNow = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .onlyOpenBusinesses()
            .limit(1)
        );

        assertThat(searchResultOpenNow.businesses.size(), is(1));

        assertThat(
            searchResultOpenAt.businesses.get(0).basicInformation.id,
            is(searchResultOpenNow.businesses.get(0).basicInformation.id)
        );
    }

    @Test
    public void it_searches_by_coordinates() {
        SearchResult searchResult = yelp.search(SearchCriteria.byCoordinates(29.426786, -98.489576));

        assertThat(searchResult.businesses.size(), greaterThan(0));
        assertThat(searchResult.businesses.get(0).basicInformation.location.city, is("San Antonio"));
    }

    @Test
    public void it_limits_the_amount_of_results() {
        SearchCriteria criteria = SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(3)
        ;
        SearchResult searchResult = yelp.search(criteria);

        assertThat(searchResult.businesses.size(), is(3));
        assertThat(searchResult.businesses.get(0).basicInformation.location.city, is("San Antonio"));
        assertThat(searchResult.businesses.get(1).basicInformation.location.city, is("San Antonio"));
        assertThat(searchResult.businesses.get(2).basicInformation.location.city, is("San Antonio"));
    }

    @Test
    public void it_paginates_a_search_result() {
        SearchResult all = yelp.search(SearchCriteria.byLocation("San Antonio").limit(4));
        SearchResult firstTwo = yelp.search(SearchCriteria.byLocation("San Antonio").limit(2));
        SearchResult lastTwo = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .offset(2)
        );

        assertThat(
            all.businesses.get(0).basicInformation.id,
            is(firstTwo.businesses.get(0).basicInformation.id)
        );
        assertThat(
            all.businesses.get(1).basicInformation.id,
            is(firstTwo.businesses.get(1).basicInformation.id)
        );
        assertThat(
            all.businesses.get(2).basicInformation.id,
            is(lastTwo.businesses.get(0).basicInformation.id)
        );
        assertThat(
            all.businesses.get(3).basicInformation.id,
            is(lastTwo.businesses.get(1).basicInformation.id)
        );
    }

    @Test
    public void it_sorts_the_search_by_given_mode() {
        SearchResult sorted = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .sortBy(REVIEW_COUNT)
        );

        assertThat(
            sorted.businesses.get(0).basicInformation.reviewCount,
            greaterThan(sorted.businesses.get(1).basicInformation.reviewCount)
        );
        assertThat(
            sorted.businesses.get(1).basicInformation.reviewCount,
            greaterThan(sorted.businesses.get(2).basicInformation.reviewCount)
        );
    }

    @Test
    public void it_searches_using_a_specific_locale() throws Exception {
        SearchResult searchResult = yelp.search(SearchCriteria
            .byLocation("San Antonio")
            .withLocale(new Locale("es", "MX"))
            .limit(1)
        );

        DetectLanguage.apiKey = languageDetectKey;
        List<Result> detected = new ArrayList<>();
        for (Category category : searchResult.businesses.get(0).basicInformation.categories)
            detected.addAll(DetectLanguage.detect(category.title));

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

        assertThat(business.basicInformation.id, is(businessId));
        assertThat(business.basicInformation.location.city, is("San Antonio"));
    }

    @Test
    public void it_uses_an_existing_token() {
        Yelp yelp = new Yelp(new Credentials(clientID, clientSecret, token));
        String businessId = "bella-on-the-river-san-antonio";

        Business business = yelp.searchById(businessId);

        assertThat(business.basicInformation.id, is(businessId));
        assertThat(business.basicInformation.location.city, is("San Antonio"));
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
