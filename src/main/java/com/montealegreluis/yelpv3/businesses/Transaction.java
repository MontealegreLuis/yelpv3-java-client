/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.Arrays;

public class Transaction {
    public final String type;
    public final String label;

    public Transaction(String type) {
        this.type = type;
        this.label = convertToWords(type);
    }

    private String convertToWords(String type) {
        return Arrays.stream(type.split("_"))
            .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
            .reduce("", (label, transaction) -> label += transaction + " ")
            .trim()
        ;
    }
}
