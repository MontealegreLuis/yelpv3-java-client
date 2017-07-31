/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.Collections;
import java.util.List;

public class Location {
    public final String address1;
    public final String address2;
    public final String address3;
    public final String city;
    public final String state;
    public final String country;
    public final String zipCode;
    public final String crossStreets;
    public final List<String> displayAddress;

    public Location(
        String address1,
        String address2,
        String address3,
        String city,
        String state,
        String country,
        String zipCode,
        String crossStreets,
        List<String> displayAddress
    ) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.crossStreets = crossStreets;
        this.displayAddress = Collections.unmodifiableList(displayAddress);
    }
}
