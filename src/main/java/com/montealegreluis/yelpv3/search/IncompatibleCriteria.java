/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

/**
 * Exception thrown when the combination of two parameters in a search will produce an error
 * response with Yelp
 * <p>
 * For instance, it is not possible to combine <code>open_now</code> and <code>open_at</code>
 * in a search
 */
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
