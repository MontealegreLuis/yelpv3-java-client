/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses.distance;

/**
 * Distances are measured in meters by Yelp, however most common values in searches are either
 * in kilometers or miles
 */
public enum UnitOfLength {
    METERS,
    KILOMETERS,
    MILES
}
