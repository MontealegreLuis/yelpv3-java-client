/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.search.SearchCategories;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchCategoryParserTest {
    @Test
    public void it_loads_all_parent_categories_available_in_the_us() {
        SearchCategories searchCategories = SearchCategoryParser.all()
            .parentCategories()
            .availableAt(new Locale("en", "US"))
        ;

        assertThat(searchCategories.size(), is(21));
    }
}
