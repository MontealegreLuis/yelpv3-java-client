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
 * Method calls can be chained.
 * <p>
 * The following will return all the parent categories available in the US:
 * <p>
 * <code>SearchCategories.parentCategories().availableAt(Locale.US)</code>
 * <p>
 * The following will return all the children categories of "restaurants" available in Mexico
 * <p>
 * <code>SearchCategories.childrenOf("restaurants").availableAt(new Locale("es", "MX"))</code>
 */
public class SearchCategories extends ArrayList<SearchCategory> {
    public SearchCategories(List<SearchCategory> categories) {
        super(categories);
    }

    public SearchCategories() {
        super();
    }

    public SearchCategories parentCategories() {
        return stream()
            .filter(SearchCategory::isParent)
            .collect(Collectors.toCollection(SearchCategories::new))
        ;
    }

    public SearchCategories availableAt(Locale locale) {
        return stream()
            .filter(category -> category.isAvailableAt(locale.getCountry()))
            .collect(Collectors.toCollection(SearchCategories::new))
        ;
    }

    public SearchCategories childrenOf(String category) {
        return stream()
            .filter(searchCategory -> searchCategory.isChildOf(category))
            .collect(Collectors.toCollection(SearchCategories::new))
        ;
    }
}
