/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.builders;

import com.montealegreluis.yelpv3.businesses.Business;

import java.util.ArrayList;
import java.util.List;

public class A {
    private static BusinessBuilder businessBuilder = new BusinessBuilder();
    private static CoordinatesBuilder coordinatesBuilder = new CoordinatesBuilder();
    private static CategoryBuilder categoryBuilder = new CategoryBuilder();

    public static CoordinatesBuilder coordinate() {
        return coordinatesBuilder;
    }

    public static CategoryBuilder category() {
        return categoryBuilder;
    }

    public static BusinessBuilder business() {
        return businessBuilder;
    }

    public static List<Business> groupOfBusinesses(int count) {
        List<Business> businesses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            businesses.add(businessBuilder.build());
        }
        return businesses;
    }
}
