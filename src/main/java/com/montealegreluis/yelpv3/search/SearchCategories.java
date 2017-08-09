/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SearchCategories extends ArrayList<SearchCategory> {
    public SearchCategories(List<SearchCategory> categories) {
        super(categories);
    }

    public SearchCategories() {
        super();
    }

    public SearchCategories main() {
        return new SearchCategories(stream()
            .filter(category -> category.parents.isEmpty())
            .collect(Collectors.toList())
        );
    }

    public SearchCategories availableAt(Locale locale) {
        return new SearchCategories(stream()
            .filter(category -> isAvailableAtCountry(locale.getCountry(), category))
            .collect(Collectors.toList())
        );
    }

    private boolean isAvailableAtCountry(String country, SearchCategory category) {
        return category.countries == null
            || category.countries.indexOf(country) > 0;
    }
}
