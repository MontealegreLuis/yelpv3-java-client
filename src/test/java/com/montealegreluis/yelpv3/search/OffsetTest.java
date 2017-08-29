/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OffsetTest {
    @Test
    public void it_does_not_allow_an_offset_greater_than_1000() {
        exception.expect(InvalidOffset.class);

        Offset.of(1001);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
}