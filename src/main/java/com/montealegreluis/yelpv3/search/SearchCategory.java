/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.Category;

import java.util.List;

public class SearchCategory extends Category {
    public final List<String> parents;
    public final List<String> countries;

    public SearchCategory(
        String alias,
        String title,
        List<String> parents,
        List<String> countries
    ) {
        super(alias, title);
        this.parents = parents;
        this.countries = countries;
    }
}
