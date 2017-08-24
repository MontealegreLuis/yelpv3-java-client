/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.montealegreluis.yelpv3.businesses.Businesses;
import com.montealegreluis.yelpv3.businesses.Category;
import com.montealegreluis.yelpv3.businesses.PricingLevel;
import com.montealegreluis.yelpv3.businesses.SearchResult;
import com.montealegreluis.yelpv3.businesses.distance.Distance;
import com.montealegreluis.yelpv3.client.AccessToken;
import com.montealegreluis.yelpv3.client.BusinessResponse;
import com.montealegreluis.yelpv3.client.Credentials;
import com.montealegreluis.yelpv3.client.ReviewsResponse;
import com.montealegreluis.yelpv3.reviews.Review;
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

import static com.montealegreluis.yelpv3.search.Attribute.DEALS;
import static com.montealegreluis.yelpv3.search.Attribute.HOT_AND_NEW;
import static com.montealegreluis.yelpv3.search.SortingMode.REVIEW_COUNT;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsNot.not;
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
        String city = "San Antonio";

        SearchResult result = yelp.search(SearchCriteria.byLocation(city)).searchResult();

        assertThat(result.total, greaterThan(0));
        assertThat(result.region.center.latitude, is(not(0)));
        assertThat(result.region.center.longitude, is(not(0)));
        assertThat(result.businesses.size(), is(20));
        assertThat(result.businesses.get(0).isInCity(city), is(true));
        assertThat(result.businesses.get(19).isInCity(city), is(true));
    }

    @Test
    public void it_includes_a_term_in_the_search() {
        String searchTerm = "bbq";
        SearchCriteria usingTerm = SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .withTerm(searchTerm)
        ;

        SearchResult result = yelp.search(usingTerm).searchResult();

        assertThat(result.businesses.size(), is(2));
        assertThat(result.businesses.get(0).isInCategory(searchTerm), is(true));
        assertThat(result.businesses.get(1).isInCategory(searchTerm), is(true));
    }

    @Test
    public void it_searches_within_a_specific_radius_in_meters() {
        Distance radius = Distance.inMeters(1000);
        SearchCriteria withinARadiusInMeters = SearchCriteria
            .byLocation("San Antonio")
            .withinARadiusOf(radius)
            .limit(2)
        ;

        // Search by radius is not strict, it might return a business a little further than expected
        Distance area = Distance.inMeters(1200);

        SearchResult result = yelp.search(withinARadiusInMeters).searchResult();

        assertThat(result.businesses.size(), is(2));
        assertThat(result.businesses.get(0).isWithinRadius(area), is(true));
        assertThat(result.businesses.get(1).isWithinRadius(area), is(true));
    }

    @Test
    public void it_searches_within_a_specific_radius_in_miles() {
        Distance radius = Distance.inMiles(1);
        SearchCriteria withinARadiusOfMiles = SearchCriteria
            .byLocation("San Antonio")
            .withinARadiusOf(radius)
            .limit(2)
        ;

        // Search by radius is not strict, it might return a business a little further than expected
        Distance area = Distance.inMiles(1.1);

        SearchResult result = yelp.search(withinARadiusOfMiles).searchResult();

        assertThat(result.businesses.size(), is(2));
        assertThat(
            String.format(
                "Business' distance %s is not within area %s",
                result.businesses.get(0).distance.toString(),
                area.toString()
            ),
            result.businesses.get(0).isWithinRadius(area.toMeters()),
            is(true)
        );
        assertThat(result.businesses.get(1).isWithinRadius(area.toMeters()), is(true));
    }

    @Test
    public void it_searches_only_open_businesses() {
        SearchCriteria onlyOpenBusinesses = SearchCriteria
            .byLocation("San Antonio")
            .openNow()
            .limit(1)
        ;

        SearchResult result = yelp.search(onlyOpenBusinesses).searchResult();

        assertThat(result.businesses.size(), is(1));

        BusinessResponse response = yelp.searchById(result.businesses.get(0).id);

        assertThat(response.business().isOpenNow(), is(true));
    }

    @Test
    public void it_searches_by_category() {
        String category = "mexican";
        SearchCriteria byCategory = SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .inCategories(category)
            .limit(2)
        ;

        SearchResult restaurants = yelp.search(byCategory).searchResult();

        assertThat(restaurants.businesses.size(), is(2));
        assertThat(restaurants.businesses.get(0).isInCategory(category), is(true));
        assertThat(restaurants.businesses.get(1).isInCategory(category), is(true));
    }

    @Test
    public void it_searches_with_a_specific_price_level() {
        SearchCriteria byPricingLevel = SearchCriteria
            .byLocation("San Antonio")
            .withPricing(PricingLevel.MODERATE)
            .limit(2)
        ;

        SearchResult result = yelp.search(byPricingLevel).searchResult();

        assertThat(result.businesses.size(), is(2));
        assertThat(result.businesses.get(0).hasPricingLevel(PricingLevel.MODERATE), is(true));
        assertThat(result.businesses.get(1).hasPricingLevel(PricingLevel.MODERATE), is(true));
    }

    @Test
    public void it_searches_with_specific_attributes() {
        SearchCriteria withMatchingAttributes = SearchCriteria
            .byLocation("San Antonio")
            .withAttributes(HOT_AND_NEW, DEALS)
            .limit(1)
        ;

        SearchResult result = yelp.search(withMatchingAttributes).searchResult();

        // I don't know how to confirm it, I can only trust... :p
        assertThat(result.businesses.size(), is(1));
    }

    @Test
    public void it_searches_businesses_opened_at_a_given_time() {
        SearchCriteria businessesOpenAtThisMoment = SearchCriteria
            .byLocation("San Antonio")
            .openAt(Instant.now().getEpochSecond())
            .limit(1)
        ;
        SearchCriteria businessesOpenNow = SearchCriteria
            .byLocation("San Antonio")
            .openNow()
            .limit(1)
        ;

        SearchResult openAt = yelp.search(businessesOpenAtThisMoment).searchResult();

        assertThat(openAt.businesses.size(), is(1));

        SearchResult openNow = yelp.search(businessesOpenNow).searchResult();

        assertThat(openNow.businesses.size(), is(1));
        assertThat(openAt.businesses.get(0).equals(openNow.businesses.get(0)), is(true));
    }

    @Test
    public void it_searches_by_coordinates() {
        SearchCriteria byCoordinates = SearchCriteria
            .byCoordinates(29.426786, -98.489576)
        ;

        SearchResult result = yelp.search(byCoordinates).searchResult();

        assertThat(result.businesses.size(), greaterThan(0));
        assertThat(result.businesses.get(0).isInCity("San Antonio"), is(true));
    }

    @Test
    public void it_limits_the_amount_of_results() {
        SearchCriteria onlyThreeBusinesses = SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(3)
        ;

        SearchResult result = yelp.search(onlyThreeBusinesses).searchResult();

        assertThat(result.businesses.size(), is(3));
        assertThat(result.businesses.get(0).isInCity("San Antonio"), is(true));
        assertThat(result.businesses.get(1).isInCity("San Antonio"), is(true));
        assertThat(result.businesses.get(2).isInCity("San Antonio"), is(true));
    }

    @Test
    public void it_paginates_a_search_result() {
        SearchCriteria withAnOffsetOf2 = SearchCriteria
            .byLocation("San Antonio")
            .limit(2)
            .offset(2)
        ;
        SearchResult all = yelp
            .search(SearchCriteria.byLocation("San Antonio").limit(4))
            .searchResult()
        ;
        SearchResult firstTwo = yelp
            .search(SearchCriteria.byLocation("San Antonio").limit(2))
            .searchResult()
        ;

        SearchResult lastTwo = yelp.search(withAnOffsetOf2).searchResult();

        assertThat(all.businesses.get(0).equals(firstTwo.businesses.get(0)), is(true));
        assertThat(all.businesses.get(1).equals(firstTwo.businesses.get(1)), is(true));
        assertThat(all.businesses.get(2).equals(lastTwo.businesses.get(0)), is(true));
        assertThat(all.businesses.get(3).equals(lastTwo.businesses.get(1)), is(true));
    }

    @Test
    public void it_sorts_the_search_by_given_mode() {
        SearchCriteria withMoreReviews = SearchCriteria
            .byLocation("San Antonio")
            .sortBy(REVIEW_COUNT)
        ;

        Businesses sorted = yelp.search(withMoreReviews).searchResult().businesses.sortByReviewCount();

        assertThat(sorted.get(0).hasMoreReviewsThan(sorted.get(1)), is(true));
        assertThat(sorted.get(1).hasMoreReviewsThan(sorted.get(2)), is(true));
    }

    @Test
    public void it_searches_using_a_specific_locale() throws Exception {
        SearchCriteria usingSpanishLocale = SearchCriteria
            .byLocation("San Antonio")
            .withLocale(new Locale("es", "MX"))
            .limit(1)
        ;

        SearchResult searchResult = yelp.search(usingSpanishLocale).searchResult();

        DetectLanguage.apiKey = languageDetectKey;
        List<Result> detected = new ArrayList<>();
        for (Category category : searchResult.businesses.get(0).categories)
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

        BusinessResponse response = yelp.searchById(businessId);

        assertThat(response.business().id, is(businessId));
        assertThat(response.business().isInCity("San Antonio"), is(true));
    }

    @Test
    public void it_gets_the_reviews_for_a_specific_business() {
        String businessId = "bella-on-the-river-san-antonio";

        ReviewsResponse response = yelp.reviews(businessId);

        assertThat(response.reviews().size(), lessThan(4));
        assertThat(response.reviews().size(), greaterThan(0));
        assertThat(response.reviews().get(0), isA(Review.class));
    }

    @Test
    public void it_uses_an_existing_token() {
        Yelp yelp = new Yelp(new Credentials(clientID, clientSecret, token));
        String businessId = "bella-on-the-river-san-antonio";

        BusinessResponse response = yelp.searchById(businessId);

        assertThat(response.business().id, is(businessId));
        assertThat(response.business().isInCity("San Antonio"), is(true));
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
