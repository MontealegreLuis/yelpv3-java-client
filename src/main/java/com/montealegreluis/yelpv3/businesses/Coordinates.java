/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONObject;

public class Coordinates {
    private final double latitude;
    private final double longitude;

    public static Coordinates from(JSONObject coordinates) {
        return new Coordinates(coordinates);
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    private Coordinates(JSONObject coordinates) {
        latitude = !coordinates.isNull("latitude") ? coordinates.getDouble("latitude") : 0;
        longitude = !coordinates.isNull("longitude") ? coordinates.getDouble("longitude") : 0;
    }
}
