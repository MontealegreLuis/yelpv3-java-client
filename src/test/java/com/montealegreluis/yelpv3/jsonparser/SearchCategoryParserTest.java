/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.search.SearchCategories;
import com.montealegreluis.yelpv3.search.SearchCategory;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class SearchCategoryParserTest {
    @Test
    public void it_loads_all_parent_categories_available_in_the_us() {
        SearchCategories searchCategories = SearchCategoryParser.all()
            .parentCategories()
            .availableAt(Locale.US)
        ;

        assertThat(searchCategories.size(), is(21));
    }

    @Test
    public void it_finds_all_the_children_of_a_given_category() {
        SearchCategories searchCategories = SearchCategoryParser.all().childrenOf("restaurants");

        int indexOfMexican = searchCategories.indexOf(new SearchCategory(
            "mexican",
            "Mexican",
            Collections.singletonList("restaurants"),
            null,
            null
        ));

        assertThat(searchCategories.size(), is(189));
        assertThat(indexOfMexican, greaterThan(0));
        assertThat(
            searchCategories.get(indexOfMexican).isChildOf("restaurants"),
            is(true)
        );
    }
}
