/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.Category;

import java.util.List;

public class SearchCategory extends Category {
    public final List<String> parents;
    public final List<String> whitelist;
    public final List<String> blacklist;

    public SearchCategory(
        String alias,
        String title,
        List<String> parents,
        List<String> whitelist,
        List<String> blacklist
    ) {
        super(alias, title);
        this.parents = parents;
        this.whitelist = whitelist; 
        this.blacklist = blacklist;
    }

    public boolean isParent() {
        return parents.isEmpty();
    }

    public boolean isAvailableAt(String country) {
        if (isBlacklistedAt(country)) return false;
        return isWhitelistedAt(country);
    }

    private boolean isWhitelistedAt(String country) {
        return whitelist == null || whitelist.indexOf(country) > 0;
    }

    private boolean isBlacklistedAt(String country) {
        return blacklist != null && blacklist.indexOf(country) > 0;
    }
}
