/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.businesses.distance.Distance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BasicInformation {
    public final double rating;
    public final PricingLevel pricingLevel;
    public final String phone;
    public final String id;
    public final boolean isClosedPermanently;
    public final List<Category> categories;
    public final int reviewCount;
    public final String name;
    public final String url;
    public final Coordinates coordinates;
    public final String image;
    public final Location location;
    public final Distance distance;
    public final List<String> transactions;

    public BasicInformation(
        double rating,
        PricingLevel pricingLevel,
        String phone,
        String id,
        boolean isClosedPermanently,
        List<Category> categories,
        int reviewCount,
        String name,
        String url,
        Coordinates coordinates,
        String image,
        Location location,
        Distance distance,
        List<String> transactions
    ) {
        this.rating = rating;
        this.pricingLevel = pricingLevel;
        this.phone = phone;
        this.id = id;
        this.isClosedPermanently = isClosedPermanently;
        this.categories = Collections.unmodifiableList(categories);
        this.reviewCount = reviewCount;
        this.name = name;
        this.url = url;
        this.coordinates = coordinates;
        this.image = image;
        this.location = location;
        this.distance = distance;
        this.transactions = Collections.unmodifiableList(transactions);
    }

    public boolean isInCity(String city) {
        return location.city.equalsIgnoreCase(city);
    }

    public boolean isWithinRadius(Distance radius) {
        return distance.smallerThan(radius);
    }

    public boolean hasPricingLevel(PricingLevel pricingLevel) {
        return this.pricingLevel.equals(pricingLevel);
    }

    public boolean hasMoreReviewsThan(BasicInformation business) {
        return reviewCount > business.reviewCount;
    }

    public boolean isInCategory(String categoryAlias) {
        return categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        BasicInformation business = (BasicInformation) other;

        return id.equals(business.id);
    }
}
