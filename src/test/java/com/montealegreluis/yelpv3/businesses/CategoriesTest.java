/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.builders.A;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CategoriesTest {
    @Test
    public void it_can_be_converted_to_a_comma_separated_value() {
        Categories categories = new Categories();

        categories.add(A.category().withAlias("restaurants").build());
        categories.add(A.category().withAlias("mexican").build());
        categories.add(A.category().withAlias("hot-dogs").build());

        assertThat(categories.toCsv(), is("restaurants,mexican,hot-dogs"));
    }
}
