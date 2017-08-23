/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DistanceTest {
    @Test
    public void it_creates_a_distance_measured_in_meters() {
        final double meters = 2000.0;
        final Distance distance = Distance.inMeters(meters);

        assertThat(distance, instanceOf(DistanceInMeters.class));
        assertThat(distance.toMeters().value, is(meters));
        assertThat(distance.toMiles().value, closeTo(1.24, 0.01));
    }

    @Test
    public void it_creates_a_distance_measured_in_miles() {
        final double miles = 5.0;
        final Distance distance = Distance.inMiles(miles);

        assertThat(distance, instanceOf(DistanceInMiles.class));
        assertThat(distance.toMeters().value, closeTo(8046.72, .01));
        assertThat(distance.toMiles().value, is(miles));
    }
}
