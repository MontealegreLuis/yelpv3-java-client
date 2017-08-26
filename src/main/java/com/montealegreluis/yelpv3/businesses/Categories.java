/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;

public class Categories extends ImmutableList<Category> {
    public Categories(List<Category> categories) {
        super(categories);
    }

    public Categories() {
        super();
    }

    /**
     * It is often useful to perform a new search using a given business' categories in order to
     * suggest similar places
     * <p>
     * This method will transform the current collection of categories into a CSV string which is
     * the format expected by <code>SearchCriteria</code>
     */
    public String toCsv() {
        return this
            .stream()
            .map(category -> category.alias)
            .reduce("", (current, category) -> String.format("%s,%s", current, category))
            .substring(1)
        ;
    }
}
