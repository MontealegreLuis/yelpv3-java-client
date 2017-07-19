/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

public class Category {
    private String alias;
    private String title;

    public Category(String alias, String title) {
        this.alias = alias;
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, alias: %s", title, alias);
    }
}
