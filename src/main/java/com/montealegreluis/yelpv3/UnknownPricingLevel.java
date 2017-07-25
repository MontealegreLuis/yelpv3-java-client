/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

public class UnknownPricingLevel extends RuntimeException {
    private UnknownPricingLevel(String format) {
        super(format);
    }

    public static UnknownPricingLevel with(String symbol) {
        return new UnknownPricingLevel(String.format("Unknown pricing level %s", symbol));
    }
}
