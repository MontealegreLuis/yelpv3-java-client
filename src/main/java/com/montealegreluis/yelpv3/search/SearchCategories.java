/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Currently, there's no endpoint for categories in Yelp's API, instead they provide a
 * <code>.json</code> file with all the categories information
 * <p>
 * This class reads that file and provides an interface to the categories information
 * <p>
 * You can find all the parents categories and all the categories available for a country
 * <p>
 * Method calls can be chained. The following code will return all the parent categories available
 * in the US:
 *<p>
 * <code>SearchCategories.main().availableAt(new Locale("US", "EN"))</code>
 */
public class SearchCategories extends ArrayList<SearchCategory> {
    public SearchCategories(List<SearchCategory> categories) {
        super(categories);
    }

    public SearchCategories() {
        super();
    }

    public SearchCategories main() {
        return new SearchCategories(stream()
            .filter(SearchCategory::isParent)
            .collect(Collectors.toList())
        );
    }

    public SearchCategories availableAt(Locale locale) {
        return new SearchCategories(stream()
            .filter(category -> category.isAvailableAt(locale.getCountry()))
            .collect(Collectors.toList())
        );
    }
}
