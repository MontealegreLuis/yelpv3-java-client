/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.builders;

import com.github.javafaker.Faker;
import com.montealegreluis.yelpv3.businesses.Coordinates;

public class CoordinatesBuilder {
    private final Faker faker;

    public CoordinatesBuilder() {
        this.faker = new Faker();
    }

    public Coordinates build() {
        return new Coordinates(
            Double.valueOf(faker.address().latitude()),
            Double.valueOf(faker.address().longitude())
        );
    }
}
