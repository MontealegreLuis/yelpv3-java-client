/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.builders;

import com.github.javafaker.Faker;
import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.Categories;
import com.montealegreluis.yelpv3.businesses.PricingLevel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class BusinessBuilder {
    private final Faker faker;
    private String id;

    BusinessBuilder() {
        faker = new Faker();
    }

    public BusinessBuilder withId(String businessId) {
        id = businessId;
        return this;
    }

    public Business build() {
        try {
            return new Business(
                faker.number().numberBetween(1, 5),
                PricingLevel.fromSymbol(String.join(
                    "",
                    Collections.nCopies(faker.number().numberBetween(1, 4), "$")
                )),
                faker.phoneNumber().phoneNumber(),
                id != null ? id : faker.internet().slug(),
                faker.bool().bool(),
                new Categories(),
                faker.number().numberBetween(1, 500),
                faker.company().name(),
                new URL(String.format("https://%s", faker.internet().url())),
                A.coordinate().build(),
                new URL(String.format("https://%s", faker.internet().url())),
                null,
                null,
                Collections.emptyList()
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
