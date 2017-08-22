/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Categories extends ArrayList<Category> {
    public Categories(List<Category> categories) {
        super(categories);
    }

    public Categories() {
        super();
    }

    public String toCsv() {
        return this
            .stream()
            .map(category -> category.alias)
            .reduce("", (current, category) -> String.format("%s,%s", current, category))
            .substring(1)
        ;
    }

    // Not completely immutable, but basic operations are covered
    public Category set(int index, Category element) {
        throw new UnsupportedOperationException();
    }
    public void add(int index, Category element) {
        throw new UnsupportedOperationException();
    }
    public Category remove(int index) {
        throw new UnsupportedOperationException();
    }
    public void sort(Comparator<? super Category> c) {
        throw new UnsupportedOperationException();
    }
}
