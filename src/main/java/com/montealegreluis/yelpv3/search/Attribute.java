/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public enum Attribute {
    HOT_AND_NEW(
        "hot_and_new",
        "Hot and new",
        "Hot and New businesses"
    ),
    REQUEST_A_QUOTE(
        "request_a_quote",
        "Can request a quote",
        "Businesses that have the Request a Quote feature"
    ),
    WAITLIST_RESERVATION(
        "waitlist_reservation",
        "Online waitlist",
        "Businesses that have an online waitlist"
    ),
    CASHBACK(
        "cashback",
        "Offer cash back",
        "Businesses that offer Cash Back"
    ),
    DEALS(
        "deals",
        "Offer deals",
        "Businesses that offer Deals"
    ),
    GENDER_NEUTRAL_RESTROOMS(
        "gender_neutral_restrooms",
        "Has gender neutral restrooms",
        "Businesses that provide gender neutral restrooms"
    );

    public final String value;
    public final String label;
    public final String description;

    Attribute(String value, String label, String description) {
        this.value = value;
        this.label = label;
        this.description = description;
    }

    /**
     * This method will be removed in v3
     */
    @Deprecated
    public String value() {
        return value;
    }

    /**
     * This method will be removed in v3
     */
    @Deprecated
    public String description() {
        return description;
    }
}
