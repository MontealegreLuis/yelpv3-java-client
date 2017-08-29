/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public class TooManyResults extends RuntimeException {
    private TooManyResults(String format) {
        super(format);
    }

    public static TooManyResults requested(Integer limit) {
        return new TooManyResults(String.format(
            "Maximum amount of results is %d, %d given", Limit.MAX_LIMIT, limit
        ));
    }
}
