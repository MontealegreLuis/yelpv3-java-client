/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

public enum Attribute {
    HOT_AND_NEW("hot_and_new", "Hot and New businesses"),
    REQUEST_A_QUOTE("request_a_quote", "Businesses that have the Request a Quote feature"),
    WAITLIST_RESERVATION("waitlist_reservation", "Businesses that have an online waitlist"),
    CASHBACK("cashback", "Businesses that offer Cash Back"),
    DEALS("deals", "Businesses that offer Deals"),
    GENDER_NEUTRAL_RESTROOMS("gender_neutral_restrooms", "Businesses that provide gender neutral restrooms");

    private final String value;
    private final String description;

    Attribute(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String value() {
        return value;
    }

    public String description() {
        return description;
    }
}
