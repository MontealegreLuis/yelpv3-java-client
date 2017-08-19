/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.builders.A;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchResultTest {
    @Test
    public void it_gets_the_coordinates_of_all_the_businesses_found() {
        final int businessesCount = 8;
        final SearchResult result = new SearchResult(
            200,
            A.groupOfBusinesses(businessesCount),
            Region.withCenter(A.coordinate().build())
        );

        assertThat(result.coordinates().size(), is(businessesCount));
    }
}
