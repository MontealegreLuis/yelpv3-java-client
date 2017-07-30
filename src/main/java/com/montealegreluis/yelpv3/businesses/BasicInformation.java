/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;

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
        this.categories = categories;
        this.reviewCount = reviewCount;
        this.name = name;
        this.url = url;
        this.coordinates = coordinates;
        this.image = image;
        this.location = location;
        this.distance = distance;
        this.transactions = transactions;
    }
}
