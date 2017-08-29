/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public class Limit {
    static final int MAX_LIMIT = 50;
    private int value;

    public static Limit of(int value) {
        return new Limit(value);
    }

    public Integer value() {
        return value;
    }

    private Limit(int value) {
        if (value > MAX_LIMIT) throw TooManyResults.requested(value);

        this.value = value;
    }
}
