/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(int statusCode, String url, String responseBody) {
        super(String.format(
            "HTTP Error occurred%nStatus code: %d%nURI: %s%nResponse body: %s",
            statusCode,
            url,
            responseBody
        ));
    }
}
