/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RadiusTest {
    @Test
    public void it_cannot_be_bigger_than_40000_meters() {
        exception.expect(AreaTooLarge.class);
        Radius.inMeters(40001);
    }

    @Test
    public void it_cannot_be_bigger_than_40_kilometers() {
        exception.expect(AreaTooLarge.class);
        Radius.inKilometers(41);
    }

    @Test
    public void it_cannot_be_bigger_than_25_miles() {
        exception.expect(AreaTooLarge.class);
        Radius.inMiles(26);
    }


    @Rule
    public ExpectedException exception = ExpectedException.none();
}