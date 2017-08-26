/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Businesses extends ImmutableList<Business> {
    public Businesses(List<Business> businesses) {
        super(businesses);
    }

    /**
     * It is often useful to perform a new search using a given business' coordinate and categories
     * in order to suggest similar places
     * <p>
     * This method will exclude that said business from the current collection.
     */
    public Businesses excluding(Business excludedBusiness) {
        List<Business> filteredBusiness = this
            .stream()
            .filter(business -> !business.equals(excludedBusiness))
            .collect(Collectors.toList());
        return new Businesses(filteredBusiness);
    }

    /**
     * Sort businesses by their review count in descending order, since Yelp may return them in a
     * different order even when review count is specified in the search
     */
    public Businesses sortByReviewCount() {
        List<Business> businesses = new ArrayList<>(this);
        businesses.sort((businessA, businessB) -> businessB.reviewCount - businessA.reviewCount);

        return new Businesses(businesses);
    }
}
