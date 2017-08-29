/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LimitTest {
    @Test
    public void it_does_not_allow_more_than_50_results() {
        exception.expect(TooManyResults.class);

        Limit.of(51);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
}
