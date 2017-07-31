/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import com.montealegreluis.yelpv3.jsonparser.JSONParser;
import com.montealegreluis.yelpv3.parser.Parser;

public class Response {
    protected final String jsonResponse;
    protected Parser parser;

    public Response(String jsonResponse) {
        this.jsonResponse = jsonResponse;
        parser = new JSONParser();
    }

    public void useParser(Parser customParser) {
        parser = customParser;
    }

    public String originalResponse() {
        return jsonResponse;
    }
}
