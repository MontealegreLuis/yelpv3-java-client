/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

import org.junit.Test;

import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.*;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DistanceTest {
    @Test
    public void it_creates_a_distance_measured_in_meters() {
        final double meters = 2000.0;
        final Distance distance = Distance.inMeters(meters);

        assertThat(distance.unit, is(METERS));
        assertThat(distance.convertTo(METERS).value, is(meters));
        assertThat(distance.convertTo(MILES).value, closeTo(1.24, 0.01));
        assertThat(distance.convertTo(KILOMETERS).value, is(2.0));
    }

    @Test
    public void it_creates_a_distance_measured_in_miles() {
        final double miles = 5.0;
        final Distance distance = Distance.inMiles(miles);

        assertThat(distance.unit, is(MILES));
        assertThat(distance.convertTo(METERS).value, closeTo(8046.72, .01));
        assertThat(distance.convertTo(KILOMETERS).value, closeTo(8.04672, .01));
        assertThat(distance.convertTo(MILES).value, is(miles));
    }

    @Test
    public void it_knows_when_its_bigger_than_another_distance() {
        Distance oneMile = Distance.inMiles(1);
        Distance oneKilometer = Distance.inMeters(1000);

        assertThat(oneMile.biggerThan(oneKilometer), is(true));
    }

    @Test
    public void it_knows_when_its_smaller_than_another_distance() {
        Distance oneKilometer = Distance.inMeters(1000);
        Distance oneMile = Distance.inMiles(1);

        assertThat(oneKilometer.smallerThan(oneMile), is(true));
    }
}
