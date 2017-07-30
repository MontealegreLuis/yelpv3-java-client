/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

public class Category {
    public final String alias;
    public final String title;

    public Category(String alias, String title) {
        this.alias = alias;
        this.title = title;
    }

    /**
     * Case insensitive comparison
     */
    public boolean hasAlias(String alias) {
        return this.alias.equalsIgnoreCase(alias);
    }

    @Override
    public String toString() {
        return String.format("Title: %s, alias: %s", title, alias);
    }
}
