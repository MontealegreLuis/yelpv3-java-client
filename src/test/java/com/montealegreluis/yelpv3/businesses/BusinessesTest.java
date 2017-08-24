/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.builders.A;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BusinessesTest {
    @Test
    public void it_excludes_a_business_from_a_collection() {
        List<Business> businesses = A.groupOfBusinesses(3);
        Business excludedBusiness = A
            .business()
            .withId("south-alamode-panini-and-gelato-company-san-antonio")
            .build()
        ;
        businesses.add(excludedBusiness);
        Businesses allBusinesses = new Businesses(businesses);

        Businesses excluding = allBusinesses.excluding(excludedBusiness);

        assertThat(excluding.contains(excludedBusiness), is(false));
    }
}
