/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

/**
 * Represents the approximate cost of a meal per person
 */
public enum PricingLevel {
    INEXPENSIVE("$", "Under $10", "Inexpensive"),
    MODERATE("$$", "$11-$30", "Moderate"),
    PRICEY("$$$", "$31-$60", "Pricey"),
    ULTRA_HIGH_END("$$$$", "Above $60", "Ultra High-End");

    public final String symbol;
    public final String description;
    public final String label;

    PricingLevel(String symbol, String description, String label) {
        this.symbol = symbol;
        this.description = description;
        this.label = label;
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

    public Integer value() {
        return ordinal() + 1;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
