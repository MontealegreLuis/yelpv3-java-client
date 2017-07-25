/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

/**
 * Represents the approximate cost of a meal per person
 */
public enum PricingLevel {
    INEXPENSIVE("$", "Under $10"),
    MODERATE("$$", "$11-$30"),
    PRICEY("$$$", "$31-$60"),
    ULTRA_HIGH_END("$$$$", "Above $60");

    private final String symbol;
    private final String description;

    PricingLevel(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public static PricingLevel fromSymbol(String symbol) {
        switch (symbol) {
            case "$":
                return INEXPENSIVE;
            case "$$":
                return MODERATE;
            case "$$$":
                return PRICEY;
            case "$$$$":
                return ULTRA_HIGH_END;
            default:
                throw UnknownPricingLevel.with(symbol);
        }
    }

    public String symbol() {
        return symbol;
    }

    public Integer value() {
        return ordinal() + 1;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
