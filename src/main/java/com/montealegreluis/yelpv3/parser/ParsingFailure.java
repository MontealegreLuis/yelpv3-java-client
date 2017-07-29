/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.parser;

import org.json.JSONException;
import org.json.JSONObject;

public class ParsingFailure extends RuntimeException {

    private ParsingFailure(String format, JSONException e) {
        super(format, e);
    }

    public static ParsingFailure producedBy(JSONObject information, JSONException exception) {
        return new ParsingFailure(
            String.format("Cannot parse object%n%s", information.toString(2)),
            exception
        );
    }
}
