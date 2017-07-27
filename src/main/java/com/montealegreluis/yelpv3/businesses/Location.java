/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private final String address1;
    private final String address2;
    private final String address3;
    private final String city;
    private final String state;
    private final String country;
    private final String zipCode;
    private final String crossStreets;
    private final List<String> displayAddress = new ArrayList<>();

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

    public String crossStreets() {
        return crossStreets;
    }

    public List<String> displayAddress() {
        return displayAddress;
    }

    private Location(JSONObject location) {
        address1 = location.getString("address1");
        address2 = !location.isNull("address2") ? location.getString("address2") : "";
        address3 = !location.isNull("address3") ? location.getString("address3") : "";
        city = location.getString("city");
        state = location.getString("state");
        country = location.getString("country");
        zipCode = location.getString("zip_code");
        crossStreets = !location.isNull("cross_streets") ? location.getString("cross_streets") : "";
        if (!location.isNull("display_address")) setDisplayAddress(location.getJSONArray("display_address"));
    }

    private void setDisplayAddress(JSONArray displayAddress) {
        for (int i = 0; i < displayAddress.length(); i++)
            this.displayAddress.add(displayAddress.getString(i));
    }
}
