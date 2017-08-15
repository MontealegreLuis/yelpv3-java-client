/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TransactionTest {
    @Test
    public void it_generates_a_valid_label_from_its_type() {
        final Transaction pickup = new Transaction("pickup");
        final Transaction delivery = new Transaction("delivery");
        final Transaction reservation = new Transaction("restaurant_reservation");

        assertThat(pickup.label, is("Pickup"));
        assertThat(delivery.label, is("Delivery"));
        assertThat(reservation.label, is("Restaurant Reservation"));
    }
}
