/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScheduleTest {
    @Test
    public void it_filters_the_hours_for_a_given_day() {
        DayOfWeek monday = DayOfWeek.MONDAY;
        Hours mondayHours = new Hours(
            DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
        );
        List<Hours> hours = Arrays.asList(
            mondayHours,
            new Hours(
                DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            )
        );
        Schedule schedule = new Schedule(true, hours);

        List<Hours> onlyMondayHours = schedule.hoursFor(monday);

        assertThat(onlyMondayHours.size(), is(1));
        assertThat(onlyMondayHours.get(0).equals(mondayHours), is(true));
    }

    @Test
    public void it_filters_schedules_with_more_than_one_hour_per_day() {
        DayOfWeek monday = DayOfWeek.MONDAY;
        Hours morningMondayHours = new Hours(
            DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)
        );
        Hours eveningMondayHours = new Hours(
            DayOfWeek.MONDAY, LocalTime.of(16, 0), LocalTime.of(20, 0)
        );
        List<Hours> hours = Arrays.asList(
            morningMondayHours,
            eveningMondayHours,
            new Hours(
                DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            ),
            new Hours(
                DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(16, 0)
            )
        );
        Schedule schedule = new Schedule(true, hours);

        List<Hours> onlyMondayHours = schedule.hoursFor(monday);

        assertThat(onlyMondayHours.size(), is(2));
        assertThat(onlyMondayHours.get(0).equals(morningMondayHours), is(true));
        assertThat(onlyMondayHours.get(1).equals(eveningMondayHours), is(true));
    }
}
