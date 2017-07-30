/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import com.montealegreluis.yelpv3.businesses.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class BusinessParser {
    static Business parseFrom(JSONObject businessInformation) {
        try {
            return new Business(
                BasicInformationParser.from(businessInformation),
                Details.from(businessInformation)
            );
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(businessInformation, exception);
        }
    }
}

class BasicInformationParser {
    static BasicInformation from(JSONObject information) {
        return new BasicInformation(
            information.getDouble("rating"),
            information.has("price") ? PricingLevel.fromSymbol(information.getString("price")) : PricingLevel.NONE,
            information.getString("phone"),
            information.getString("id"),
            information.getBoolean("is_closed"),
            setCategories(information.getJSONArray("categories")),
            information.getInt("review_count"),
            information.getString("name"),
            information.getString("url"),
            Coordinates.from(information.getJSONObject("coordinates")),
            information.getString("image_url"),
            Location.from(information.getJSONObject("location")),
            !information.isNull("distance") ? new Distance(information.getDouble("distance")) : null,
            setTransactions(information.getJSONArray("transactions"))
        );
    }

    private static List<Category> setCategories(JSONArray businessCategories) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < businessCategories.length(); i++)
            categories.add(Category.from(businessCategories.getJSONObject(i)));
        return categories;
    }

    private static List<String> setTransactions(JSONArray registeredTransactions) {
        List<String> transactions = new ArrayList<>();
        for (int i = 0; i < registeredTransactions.length(); i++)
            transactions.add(registeredTransactions.getString(i));
        return transactions;
    }
}
