/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

/**
 * Represents the approximate cost of a meal per person
 */
public enum PricingLevel {
    LOW_COST("$", "under $10"),
    AVERAGE("$$", "$11-$30"),
    ABOVE_AVERAGE("$$$", "$31-$60"),
    EXPENSIVE("$$$$", "above $60");

    private final String symbol;
    private final String description;

    PricingLevel(String symbol, String description) {
        this.symbol = symbol;
        this.description = description;
    }

    public static PricingLevel fromSymbol(String symbol) {
        switch (symbol) {
            case "$":
                return LOW_COST;
            case "$$":
                return AVERAGE;
            case "$$$":
                return ABOVE_AVERAGE;
            case "$$$$":
                return EXPENSIVE;
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
