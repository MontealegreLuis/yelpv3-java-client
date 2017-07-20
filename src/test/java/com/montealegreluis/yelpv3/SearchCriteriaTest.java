/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.montealegreluis.yelpv3.Attribute.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class SearchCriteriaTest {
    @Test
    public void it_does_not_allow_a_radius_bigger_than_40000_meters() {
        exception.expect(RuntimeException.class);

        SearchCriteria
            .byLocation("San Antonio")
            .withinARadiusOf(40001)
        ;
    }

    @Test
    public void it_does_not_allow_more_than_50_results() {
        exception.expect(RuntimeException.class);

        SearchCriteria
            .byCoordinates(29.426786, -98.489576)
            .limit(51)
        ;
    }

    @Test
    public void it_adds_several_attributes() {
        SearchCriteria criteria = SearchCriteria
            .byLocation("San Antonio")
            .withAttributes(CASHBACK, DEALS, GENDER_NEUTRAL_RESTROOMS)
        ;

        assertThat(
            criteria.toString(),
            containsString("cashback,deals,gender_neutral_restrooms")
        );
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
}