/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import org.json.JSONObject;

public class Category {
    private String alias;
    private String title;

    public static Category from(JSONObject category) {
        return new Category(category.getString("alias"), category.getString("title"));
    }

    public String getAlias() {
        return alias;
    }

    /**
     * Case insensitive comparison
     */
    public boolean hasAlias(String alias) {
        return this.alias.equalsIgnoreCase(alias);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, alias: %s", title, alias);
    }

    private Category(String alias, String title) {
        this.alias = alias;
        this.title = title;
    }
}
