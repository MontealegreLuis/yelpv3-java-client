/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

public class IncompatibleCriteria extends RuntimeException {
    private IncompatibleCriteria(String format) {
        super(format);
    }

    public static IncompatibleCriteria mixing(String existing, String current) {
        return new IncompatibleCriteria(String.format(
            "Cannot mix existing %s criteria with %s", existing, current
        ));
    }
}
