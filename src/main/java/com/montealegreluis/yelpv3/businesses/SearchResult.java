/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResult {
    public final int total;
    public final List<BasicInformation> businesses;
    public final Region region;

    public SearchResult(int total, List<BasicInformation> businesses, Region region) {
        this.total = total;
        this.businesses = Collections.unmodifiableList(businesses);
        this.region = region;
    }

    public List<Coordinates> coordinates() {
        return businesses
            .stream()
            .map(business -> business.coordinates)
            .collect(Collectors.toList())
        ;
    }
}
