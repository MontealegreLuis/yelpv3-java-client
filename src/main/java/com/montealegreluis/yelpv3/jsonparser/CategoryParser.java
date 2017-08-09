/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.businesses.Category;
import org.json.JSONObject;

public class CategoryParser {
    public static Category from(JSONObject category) {
        return new Category(
            category.getString("alias"),
            category.getString("title")
        );
    }
}
