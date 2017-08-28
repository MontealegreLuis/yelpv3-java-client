/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.distance.Distance;
import org.junit.Test;

import static com.montealegreluis.yelpv3.businesses.PricingLevel.MODERATE;
import static com.montealegreluis.yelpv3.search.Attribute.DEALS;
import static com.montealegreluis.yelpv3.search.Attribute.HOT_AND_NEW;
import static com.montealegreluis.yelpv3.search.SortingMode.REVIEW_COUNT;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryStringTest {
    @Test
    public void it_can_be_represented_as_a_query_string() {
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.withTerm("restaurants");
        criteria.withinARadiusOf(Distance.inMiles(2));
        criteria.inCategories("mexican");
        criteria.withPricing(MODERATE);
        criteria.withAttributes(HOT_AND_NEW, DEALS);
        criteria.openNow();
        criteria.limit(5);
        criteria.offset(5);
        criteria.sortBy(REVIEW_COUNT);
        QueryString query = criteria.toQueryString();

        String queryString = query.forPage(2).toString();

        assertThat(
            queryString,
            is("?open_now=true&offset=5&price=2&limit=5&location=San+Antonio&term=restaurants&attributes=hot_and_new%2Cdeals&categories=mexican&sort_by=review_count&radius=3218")
        );
    }

    @Test
    public void it_gets_build_correctly_when_the_current_category_is_replaced() {
        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.inCategories("mexican");
        QueryString query = criteria.toQueryString();

        String queryString = query.inCategory("newmexican").forPage(2).toString();

        assertThat(
            queryString,
            is("?offset=20&location=San+Antonio&categories=newmexican")
        );
    }
}
