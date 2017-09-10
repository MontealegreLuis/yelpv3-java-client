/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public enum SortingMode {
    BEST_MATCH("Best match"), // Default sorting mode
    RATING("Highest rated"),
    REVIEW_COUNT("Most Reviewed"),
    DISTANCE("Closest");

    public final String label;

    SortingMode(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
