/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
    public final List<String> displayAddress = new ArrayList<>();

    public static Location from(JSONObject location) {
        return new Location(location);
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
