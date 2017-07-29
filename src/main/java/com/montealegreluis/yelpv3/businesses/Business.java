/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import com.montealegreluis.yelpv3.parser.ParsingFailure;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class Business {
    public final BasicInformation basicInformation;
    public final Details details;

    public static Business from(JSONObject information) {
        try {
            return new Business(information);
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(information, exception);
        }
    }

    private Business(JSONObject information) {
        basicInformation = BasicInformation.from(information);
        details = Details.from(information);
    }

    public boolean isInCategory(String categoryAlias) {
        return basicInformation.categories
            .stream()
            .filter(category -> category.hasAlias(categoryAlias))
            .collect(Collectors.toList())
            .size() > 0
        ;
    }
}
