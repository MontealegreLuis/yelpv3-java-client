/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.builders;

import com.github.javafaker.Faker;
import com.montealegreluis.yelpv3.businesses.Category;

public class CategoryBuilder {
    private final Faker faker;
    private String alias;

    CategoryBuilder() {
        faker = new Faker();
    }

    public CategoryBuilder withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Category build() {
        Category category = new Category(
            alias(),
            faker.lorem().sentence(faker.number().numberBetween(1, 3))
        );
        reset();
        return category;
    }

    private void reset() {
        alias = null;
    }

    private String alias() {
        return alias != null ? alias : faker.lorem().word();
    }
}
