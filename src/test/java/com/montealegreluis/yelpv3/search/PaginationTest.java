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
        int onePage = 2;
        int pageSize = 5;
        Pagination pagination = criteria.limit(pageSize).pagination(onePage);

        assertThat(pagination.hasPages(), is(false));
    }

    @Test
    public void it_calculates_how_many_pages_can_be_built() {
        int pageSize = 5;
        int twelvePages = 57;
        int pageTwelve = 12;
        Pagination pagination = criteria.limit(pageSize).pagination(twelvePages);

        assertThat(pagination.last(), is(pageTwelve));
    }

    @Test
    public void it_calculates_total_pages_when_total_matches_exactly() {
        int pageSize = 5;
        int twelvePages = 60;
        int pageTwelve = 12;
        Pagination pagination = criteria.limit(pageSize).pagination(twelvePages);

        assertThat(pagination.last(), is(pageTwelve));
    }

    @Test
    public void it_knows_when_there_is_no_previous_page() {
        int pageSize = 5;
        int twoPages = 10;
        Pagination pagination = criteria.limit(pageSize).pagination(twoPages);

        assertThat(pagination.hasPrevious(), is(false));
    }

    @Test
    public void it_calculates_the_previous_page() {
        int page4 = 4;
        int offsetPage5 = 20;
        int pageSize = 5;
        int fivePages = 22;
        Pagination pagination = criteria.limit(pageSize).offset(offsetPage5).pagination(fivePages);

        assertThat(pagination.previous(), is(page4));
    }

    @Test
    public void it_knows_when_there_is_no_next_page() {
        int pageSize = 5;
        int offsetPage3 = 10;
        int threePages = 13;
        Pagination pagination = criteria.limit(pageSize).offset(offsetPage3).pagination(threePages);

        assertThat(pagination.hasNext(), is(false));
    }

    @Test
    public void it_calculates_the_next_page() {
        int page5 = 5;
        int offsetPage4 = 15;
        int pageSize = 5;
        int sixPages = 27;
        Pagination pagination = criteria.limit(pageSize).offset(offsetPage4).pagination(sixPages);

        assertThat(pagination.next(), is(page5));
    }

    @Test
    public void it_calculates_the_first_page() {
        int page1 = 1;
        int pageSize = 5;
        int eightPages = 40;
        Pagination pagination = criteria.limit(pageSize).pagination(eightPages);

        assertThat(pagination.first(), is(page1));
    }

    @Test
    public void it_defaults_to_the_first_page_if_no_positive_value_for_page_is_provided() {
        int negativeOffset = -3;
        int page2 = 2;
        int twoPages = 30;
        Pagination pagination = criteria.offset(negativeOffset).pagination(twoPages);

        assertThat(pagination.hasPrevious(), is(false));
        assertThat(pagination.next(), is(page2));
    }

    @Test
    public void it_defaults_to_the_last_page_if_bigger_value_for_page_is_given() {
        int pageSize = 5;
        int threePages = 15;
        int page21 = 100;
        Pagination pagination = criteria.limit(pageSize).offset(page21).pagination(threePages);

        assertThat(pagination.hasNext(), is(false));
        assertThat(pagination.previous(), is(pagination.last() - 1));
    }

    @Test
    public void it_defaults_to_the_maximum_offset_allowed_by_yelp() {
        int pageSize = 20;
        int hundredPages = 2000;
        int page55 = 1020;
        int allowedPageCount = 50;
        Pagination pagination = criteria.limit(pageSize).offset(page55).pagination(hundredPages);

        assertThat(pagination.hasNext(), is(false));
        assertThat(pagination.exceedsAPILimit(), is(true));
        assertThat(pagination.last(), is(allowedPageCount));
    }

    @Test
    public void it_knows_the_current_page() {
        int pageSize = 5;
        int offsetPage7 = 30;
        int tenPages = 48;
        int pageSeven = 7;
        Pagination pagination = criteria.limit(pageSize).offset(offsetPage7).pagination(tenPages);

        assertThat(pagination.currentPage(), is(pageSeven));
    }

    private SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
}