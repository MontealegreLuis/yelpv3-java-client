/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.search.SearchCategories;
import com.montealegreluis.yelpv3.search.SearchCategory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Since there's no endpoint to get all  the available categories in Yelp, this class will read them
 * from the <code>categories.json</code> file
 * <p>
 * This class is useful if you want to populate a drop down with the available categories in a page
 */
public class SearchCategoryParser {
    private static final SearchCategories categories = new SearchCategories();

    public static SearchCategories all() {
        if (categories.isEmpty()) populate();
        return categories;
    }

    private static void populate() {
        JSONArray jsonCategories = new JSONArray(readCategories());
        for (int i = 0; i < jsonCategories.length(); i++)
            categories.add(parseSearchCategory(jsonCategories.getJSONObject(i)));
    }

    private static String readCategories() {
        ClassLoader classLoader = SearchCategoryParser.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("categories.json");
        Scanner scanner = new Scanner(stream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private static SearchCategory parseSearchCategory(JSONObject category) {
        List<String> whitelist = null;
        List<String> blacklist = null;
        List<String> parents = extract(category.getJSONArray("parents"));

        if (!category.isNull("country_whitelist"))
            whitelist = extract(category.getJSONArray("country_whitelist"));
        if (!category.isNull("country_blacklist"))
            blacklist = extract(category.getJSONArray("country_blacklist"));

        return new SearchCategory(
            category.getString("alias"),
            category.getString("title"),
            parents,
            whitelist,
            blacklist
        );
    }

    private static List<String> extract(JSONArray jsonArray) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) items.add(jsonArray.getString(i));
        return items;
    }
}
