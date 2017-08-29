/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaginationTest {
    @Test
    public void it_knows_when_it_has_a_single_page() {
        int resultsCount = 2;
        int pageSize = 5;
        criteria.limit(Limit.of(pageSize));

        Pagination pagination = criteria.pagination(resultsCount);

        assertThat(pagination.hasPages(), is(false));
    }

    @Test
    public void it_calculates_how_many_pages_can_be_built() {
        int pageSize = 5;
        int countForTwelvePages = 57;
        int pageTwelve = 12;
        criteria.limit(Limit.of(pageSize));

        Pagination pagination = criteria.pagination(countForTwelvePages);

        assertThat(pagination.last(), is(pageTwelve));
    }

    @Test
    public void it_calculates_total_pages_when_total_matches_exactly() {
        int pageSize = 5;
        int resultsForTwelvePages = 60;
        int pageTwelve = 12;
        criteria.limit(Limit.of(pageSize));

        Pagination pagination = criteria.pagination(resultsForTwelvePages);

        assertThat(pagination.last(), is(pageTwelve));
    }

    @Test
    public void it_knows_when_there_is_no_previous_page() {
        int pageSize = 5;
        int resultsForTwoPages = 10;
        criteria.limit(Limit.of(pageSize));

        Pagination pagination = criteria.pagination(resultsForTwoPages);

        assertThat(pagination.hasPrevious(), is(false));
        assertThat(pagination.isFirst(), is(true));
    }

    @Test
    public void it_calculates_the_previous_page() {
        int page4 = 4;
        int offsetPage5 = 20;
        int pageSize = 5;
        int resultsForFivePages = 22;
        criteria.limit(Limit.of(pageSize));
        criteria.offset(Offset.of(offsetPage5));

        Pagination pagination = criteria.pagination(resultsForFivePages);

        assertThat(pagination.previous(), is(page4));
    }

    @Test
    public void it_knows_when_there_is_no_next_page() {
        int pageSize = 5;
        int offsetPage3 = 10;
        int resultsForThreePages = 13;
        criteria.limit(Limit.of(pageSize));
        criteria.offset(Offset.of(offsetPage3));

        Pagination pagination = criteria.pagination(resultsForThreePages);

        assertThat(pagination.hasNext(), is(false));
        assertThat(pagination.isLast(), is(true));
    }

    @Test
    public void it_calculates_the_next_page() {
        int page5 = 5;
        int offsetPage4 = 15;
        int pageSize = 5;
        int resultsForSixPages = 27;
        criteria.limit(Limit.of(pageSize));
        criteria.offset(Offset.of(offsetPage4));

        Pagination pagination = criteria.pagination(resultsForSixPages);

        assertThat(pagination.next(), is(page5));
    }

    @Test
    public void it_calculates_the_first_page() {
        int page1 = 1;
        int pageSize = 5;
        int resultsForEightPages = 40;
        criteria.limit(Limit.of(pageSize));

        Pagination pagination = criteria.pagination(resultsForEightPages);

        assertThat(pagination.first(), is(page1));
        assertThat(pagination.isFirst(), is(true));
    }

    @Test
    public void it_defaults_to_the_first_page_if_no_positive_value_for_page_is_provided() {
        int negativeOffset = -3;
        int page2 = 2;
        int resultsForTwoPages = 30;
        criteria.offset(Offset.of(negativeOffset));

        Pagination pagination = criteria.pagination(resultsForTwoPages);

        assertThat(pagination.hasPrevious(), is(false));
        assertThat(pagination.next(), is(page2));
        assertThat(pagination.isFirst(), is(true));
    }

    @Test
    public void it_defaults_to_the_last_page_if_bigger_value_for_page_is_given() {
        int pageSize = 5;
        int resultsForThreePages = 15;
        int page21 = 100;
        criteria.limit(Limit.of(pageSize));
        criteria.offset(Offset.of(page21));

        Pagination pagination = criteria.pagination(resultsForThreePages);

        assertThat(pagination.hasNext(), is(false));
        assertThat(pagination.isLast(), is(true));
    }

    @Test
    public void it_knows_the_current_page() {
        int pageSize = 5;
        int offsetPage7 = 30;
        int resultsForTenPages = 48;
        int pageSeven = 7;
        criteria.limit(Limit.of(pageSize));
        criteria.offset(Offset.of(offsetPage7));

        Pagination pagination = criteria.pagination(resultsForTenPages);

        assertThat(pagination.currentPage(), is(pageSeven));
    }

    private SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
}