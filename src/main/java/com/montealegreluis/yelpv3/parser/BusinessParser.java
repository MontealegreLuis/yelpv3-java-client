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
                DetailsParser.from(businessInformation)
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
            buildCategories(information.getJSONArray("categories")),
            information.getInt("review_count"),
            information.getString("name"),
            information.getString("url"),
            CoordinatesParser.from(information.getJSONObject("coordinates")),
            information.getString("image_url"),
            Location.from(information.getJSONObject("location")),
            !information.isNull("distance") ? new Distance(information.getDouble("distance")) : null,
            buildTransactions(information.getJSONArray("transactions"))
        );
    }

    private static List<Category> buildCategories(JSONArray businessCategories) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < businessCategories.length(); i++)
            categories.add(Category.from(businessCategories.getJSONObject(i)));
        return categories;
    }

    private static List<String> buildTransactions(JSONArray registeredTransactions) {
        List<String> transactions = new ArrayList<>();
        for (int i = 0; i < registeredTransactions.length(); i++)
            transactions.add(registeredTransactions.getString(i));
        return transactions;
    }
}

class CoordinatesParser {
    static Coordinates from(JSONObject coordinates) {
        return new Coordinates(
            !coordinates.isNull("latitude") ? coordinates.getDouble("latitude") : 0,
            !coordinates.isNull("longitude") ? coordinates.getDouble("longitude") : 0
        );
    }
}

class DetailsParser {
    static Details from(JSONObject information) {
        return new Details(
            !information.isNull("is_claimed") && information.getBoolean("is_claimed"),
            !information.isNull("photos") ? buildPhotos(information.getJSONArray("photos")) : null,
            !information.isNull("hours") ? Schedule.from(information.getJSONArray("hours")) : null
        );
    }

    private static List<String> buildPhotos(JSONArray businessPhotos) {
        List<String> photos = new ArrayList<>();

        for (int i = 0; i < businessPhotos.length(); i++) photos.add(businessPhotos.getString(i));

        return photos;
    }
}
