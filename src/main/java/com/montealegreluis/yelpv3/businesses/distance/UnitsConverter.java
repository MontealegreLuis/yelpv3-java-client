/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

import java.util.HashMap;
import java.util.Map;

import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.KILOMETERS;
import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.METERS;
import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.MILES;

/**
 * Converts values between <code>UnitOfLength</code>s. For instance
 * <p>
 * <code>UnitsConverter.convert(1000, METERS, KILOMETERS)</code>
 * <p>
 * Will return <code>1.0</code> (1000m = 1km)
 */
class UnitsConverter {
    private static Map<UnitOfLength, Map<UnitOfLength, Double>> equivalences = new HashMap<>();

    static {
        Map<UnitOfLength, Double> toMeters = new HashMap<>();
        toMeters.put(METERS, 1.0);
        toMeters.put(KILOMETERS, 0.001);
        toMeters.put(MILES, 0.000621371);
        equivalences.put(METERS, toMeters);

        Map<UnitOfLength, Double> toKilometers = new HashMap<>();
        toKilometers.put(KILOMETERS, 1.0);
        toKilometers.put(METERS, 1000.0);
        toKilometers.put(MILES, 0.621371);
        equivalences.put(KILOMETERS, toKilometers);

        Map<UnitOfLength, Double> toMiles = new HashMap<>();
        toMiles.put(MILES, 1.0);
        toMiles.put(METERS, 1609.344);
        toMiles.put(KILOMETERS, 1.609344);
        equivalences.put(MILES, toMiles);
    }

    static double convert(double value, UnitOfLength from, UnitOfLength to) {
        return value * equivalences.get(from).get(to);
    }
}
