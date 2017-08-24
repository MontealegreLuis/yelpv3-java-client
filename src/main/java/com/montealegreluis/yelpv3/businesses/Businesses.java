/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;
import java.util.stream.Collectors;

public class Businesses extends ImmutableList<Business> {
    public Businesses(List<Business> businesses) {
        super(businesses);
    }

    public Businesses excluding(Business excludedBusiness) {
        List<Business> filteredBusiness = this
            .stream()
            .filter(business -> !business.equals(excludedBusiness))
            .collect(Collectors.toList())
        ;
        return new Businesses(filteredBusiness);
    }
}
