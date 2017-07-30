/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.stream.Collectors;

public class Business {
    public final BasicInformation basicInformation;
    public final Details details;

    public Business(BasicInformation basicInformation, Details details) {
        this.basicInformation = basicInformation;
        this.details = details;
    }

    public boolean isInCity(String city) {
        return this.basicInformation.location.city.equalsIgnoreCase(city);
    }

    public boolean isWithinRadius(int radiusInMeters) {
        return basicInformation.distance.meters <= radiusInMeters;
    }

    public boolean isOpenNow() {
        return details.schedule.isOpenNow;
    }

    public boolean hasPricingLevel(PricingLevel pricingLevel) {
        return basicInformation.pricingLevel.equals(pricingLevel);
    }

    public boolean hasMoreReviewsThan(Business business) {
        return basicInformation.reviewCount > business.basicInformation.reviewCount;
    }

    public boolean isInCategory(String categoryAlias) {
        return basicInformation.categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        return basicInformation.id.equals(business.basicInformation.id);
    }
}
