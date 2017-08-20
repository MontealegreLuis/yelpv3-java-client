/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.builders.A;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchResultTest {
    @Test
    public void it_creates_a_custom_map_for_the_businesses_in_a_search_result() {
        int businessesCount = 8;
        SearchResult result = new SearchResult(
            200,
            A.groupOfBusinesses(businessesCount),
            Region.withCenter(A.coordinate().build())
        );

        List<Map<String, Object>> customResult = result.businessesToMap(businesses -> businesses
            .stream()
            .map(business -> {
                Map<String, Object> businessInformation = new HashMap<>();
                businessInformation.put("id", business.id);
                businessInformation.put("name", business.name);
                businessInformation.put("coordinates", business.coordinates);
                return businessInformation;
            })
            .collect(Collectors.toList()))
        ;

        assertThat(customResult.size(), is(8));
        Map firstBusiness = customResult.get(0);
        assertThat(firstBusiness.containsKey("id"), is(true));
        assertThat(firstBusiness.get("id"), instanceOf(String.class));
        assertThat(firstBusiness.containsKey("name"), is(true));
        assertThat(firstBusiness.get("name"), instanceOf(String.class));
        assertThat(firstBusiness.containsKey("coordinates"), is(true));
        assertThat(firstBusiness.get("coordinates"), instanceOf(Coordinates.class));
    }
}
