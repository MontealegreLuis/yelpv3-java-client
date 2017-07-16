/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import org.json.JSONObject;

public class Location {
    private final String address1;
    private final String address2;
    private final String address3;
    private final String city;
    private final String state;
    private final String country;
    private final String zipCode;

    public static Location from(JSONObject location) {
        return new Location(location);
    }

    public String address1() {
        return address1;
    }

    public String address2() {
        return address2;
    }

    public String address3() {
        return address3;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String country() {
        return country;
    }

    public String zipCode() {
        return zipCode;
    }

    private Location(JSONObject location) {
        address1 = location.getString("address1");
        address2 = !location.isNull("address2") ? location.getString("address2") : "";
        address3 = !location.isNull("address3") ? location.getString("address3") : "";
        city = location.getString("city");
        state = location.getString("state");
        country = location.getString("country");
        zipCode = location.getString("zip_code");
    }
}
