/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaginationTest {
    @Test
    public void it_knows_when_it_has_no_pages() {
        int notMoreThanOnePage = 2;
        int pageSize = 5;
        pagination = Pagination.fromSearch(criteria.limit(pageSize), notMoreThanOnePage);

        assertThat(pagination.hasPages(), is(false));
    }

    @Test
    public void it_calculates_how_many_pages_can_be_built() {
        int pageSize = 5;
        int twelvePages = 57;
        int lastPage = 12;
        pagination = Pagination.fromSearch(criteria.limit(pageSize), twelvePages);

        assertThat(pagination.last(), is(lastPage));
    }

    @Test
    public void it_calculates_total_pages_when_total_matches_exactly() {
        int pageSize = 5;
        int twelvePages = 60;
        int lasPage = 12;
        pagination = Pagination.fromSearch(criteria.limit(pageSize), twelvePages);

        assertThat(pagination.last(), is(lasPage));
    }

    @Test
    public void it_knows_when_there_is_no_previous_page() {
        int pageSize = 5;
        int total = 10;
        pagination = Pagination.fromSearch(criteria.limit(pageSize), total);

        assertThat(pagination.hasPrevious(), is(false));
    }

    @Test
    public void it_calculates_the_previous_page() {
        int previousPage = 4;
        int offsetPage5 = 20;
        int pageSize = 5;
        int total = 22;
        pagination = Pagination.fromSearch(criteria.limit(pageSize).offset(offsetPage5), total);

        assertThat(pagination.previous(), is(previousPage));
    }

    @Test
    public void it_knows_when_there_is_no_next_page() {
        int pageSize = 5;
        int offsetPage3 = 10;
        int threePages = 13;
        pagination = Pagination.fromSearch(criteria.limit(pageSize).offset(offsetPage3), threePages);

        assertThat(pagination.hasNext(), is(false));
    }

    @Test
    public void it_calculates_the_next_page() {
        int nextPage = 5;
        int offsetPage4 = 15;
        pagination = Pagination.fromSearch(criteria.limit(5).offset(offsetPage4), 27);

        assertThat(pagination.next(), is(nextPage));
    }

    @Test
    public void it_calculates_the_first_page() {
        int firstPage = 1;
        pagination = Pagination.fromSearch(criteria.limit(5), 40);

        assertThat(pagination.first(), is(firstPage));
    }

    @Test
    public void it_defaults_to_the_first_page_if_no_positive_value_for_page_is_provided() {
        int secondPage = 2;
        int negativeOffset = -3;
        pagination = Pagination.fromSearch(criteria.offset(negativeOffset), 30);

        assertThat(pagination.hasPrevious(), is(false));
        assertThat(pagination.next(), is(secondPage));
    }

    @Test
    public void it_defaults_to_the_last_page_if_bigger_value_for_page_is_given() {
        int offsetBiggerThanPossible = 100;
        pagination = Pagination.fromSearch(
            criteria.limit(5).offset(offsetBiggerThanPossible),
            15
        );

        assertThat(pagination.hasNext(), is(false));
        assertThat(pagination.previous(), is(pagination.last() - 1));
    }

    private Pagination pagination;
    private SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
}