/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.client;

import com.montealegreluis.yelpv3.businesses.SearchResult;

public class SearchResponse extends Response {
    public static SearchResponse fromOriginalResponse(String jsonResponse) {
        return new SearchResponse(jsonResponse);
    }

    public SearchResult searchResult() {
        return parser.searchResult(jsonResponse);
    }

    private SearchResponse(String jsonResponse) {
        super(jsonResponse);
    }
}
