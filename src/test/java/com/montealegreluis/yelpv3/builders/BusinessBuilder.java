/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.builders;

import com.github.javafaker.Faker;
import com.montealegreluis.yelpv3.businesses.BasicInformation;
import com.montealegreluis.yelpv3.businesses.PricingLevel;

import java.util.Collections;

public class BusinessBuilder {
    private final Faker faker;

    BusinessBuilder() {
        faker = new Faker();
    }

    public BasicInformation build() {
        return new BasicInformation(
            faker.number().numberBetween(1, 5),
            PricingLevel.fromSymbol(String.join(
                "",
                Collections.nCopies(faker.number().numberBetween(1, 4), "$")
            )),
            faker.phoneNumber().phoneNumber(),
            faker.internet().slug(),
            faker.bool().bool(),
            Collections.emptyList(),
            faker.number().numberBetween(1, 500),
            faker.company().name(),
            faker.internet().url(),
            A.coordinate().build(),
            faker.internet().url(),
            null,
            null,
            Collections.emptyList()
        );
    }
}
