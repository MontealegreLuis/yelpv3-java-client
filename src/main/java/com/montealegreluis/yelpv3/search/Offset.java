/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public class Offset {
    static final int MAX_OFFSET = 1000;
    private final int value;

    private Offset(int value) {
        if (value> MAX_OFFSET) throw InvalidOffset.of(value);

        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public static Offset of(int value) {
        return new Offset(value);
    }
}
