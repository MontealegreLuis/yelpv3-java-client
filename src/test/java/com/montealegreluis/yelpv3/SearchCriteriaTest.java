/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.montealegreluis.yelpv3.search.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Instant;

import static com.montealegreluis.yelpv3.businesses.PricingLevel.*;
import static com.montealegreluis.yelpv3.search.Attribute.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchCriteriaTest {
    @Test
    public void it_does_not_allow_a_radius_bigger_than_40000_meters() {
        exception.expect(AreaTooLarge.class);

        SearchCriteria
            .byLocation("San Antonio")
            .withinARadiusOf(Radius.inMeters(40001))
        ;
    }

    @Test
    public void it_does_not_allow_more_than_50_results() {
        exception.expect(TooManyResults.class);

        SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(51)
        ;
    }

    @Test
    public void it_does_not_allow_searches_with_open_at_and_open_now() {
        exception.expect(IncompatibleCriteria.class);

        SearchCriteria invalidCriteria = SearchCriteria.byCoordinates(29.426786, -98.489576);
        invalidCriteria.openNow();
        invalidCriteria.openAt(Instant.now().getEpochSecond());
    }

    @Test
    public void it_adds_several_attributes() {
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.withAttributes(CASHBACK, DEALS, GENDER_NEUTRAL_RESTROOMS);

        assertThat(
            criteria.toString(),
            containsString("cashback,deals,gender_neutral_restrooms")
        );
    }

    @Test
    public void it_adds_several_pricing_levels() {
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.withPricing(INEXPENSIVE, MODERATE, PRICEY);

        assertThat(criteria.toQueryString().toString(), containsString("price=1%2C2%2C3"));
    }


    @Test
    public void it_has_access_to_the_current_limit_and_offset_values() {
        int limit = 5;
        int offset = 15;
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.limit(limit);
        criteria.offset(offset);

        assertThat(criteria.limit(), is(limit));
        assertThat(criteria.offset(), is(offset));
    }

    @Test
    public void it_has_default_values_for_limit_and_offset() {
        int defaultLimit = 20;
        int defaultOffset = 0;
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");

        assertThat(criteria.limit(), is(defaultLimit));
        assertThat(criteria.offset(), is(defaultOffset));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
}