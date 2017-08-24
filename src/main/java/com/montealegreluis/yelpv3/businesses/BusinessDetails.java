/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.businesses.distance.Distance;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public class BusinessDetails extends Business {
    public final boolean isClaimed;
    public final List<URL> photos;
    public final Schedule schedule;

    public BusinessDetails(
        double rating,
        PricingLevel pricingLevel,
        String phone,
        String id,
        boolean isClosedPermanently,
        Categories categories,
        int reviewCount,
        String name,
        URL url,
        Coordinates coordinates,
        URL image,
        Location location,
        Distance distance,
        List<Transaction> transactions,
        boolean isClaimed,
        List<URL> photos,
        Schedule schedule
    ) {
        super(
            rating,
            pricingLevel,
            phone,
            id,
            isClosedPermanently,
            categories,
            reviewCount,
            name,
            url,
            coordinates,
            image,
            location,
            distance,
            transactions
        );
        this.isClaimed = isClaimed;
        this.photos = Collections.unmodifiableList(photos);
        this.schedule = schedule;
    }

    public boolean isOpenNow() {
        return schedule.isOpenNow;
    }
}
