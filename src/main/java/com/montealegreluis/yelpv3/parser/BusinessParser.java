/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import com.montealegreluis.yelpv3.businesses.BasicInformation;
import com.montealegreluis.yelpv3.businesses.Business;
import com.montealegreluis.yelpv3.businesses.Details;
import org.json.JSONException;
import org.json.JSONObject;

public class BusinessParser {
    public static Business parseFrom(JSONObject businessInformation) {
        try {
            return new Business(
                BasicInformation.from(businessInformation),
                Details.from(businessInformation)
            );
        } catch (JSONException exception) {
            throw ParsingFailure.producedBy(businessInformation, exception);
        }
    }
}
