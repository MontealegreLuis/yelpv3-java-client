/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private Map<String, String> parameters;
    private int pageSize;
    private int page;

    public static QueryString build(Map<String, String> parameters, int pageSize) {
        return new QueryString(parameters, pageSize);
    }

    public QueryString inCategory(String category) {
        QueryString queryString = new QueryString(new HashMap<>(parameters), pageSize);
        queryString.parameters.put("categories", category);
        return queryString;
    }

    public QueryString forPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public String toString() {
        HashMap<String, String> queryString = new HashMap<>(parameters);
        queryString.put("offset", offset());
        return queryString.entrySet().stream()
            .map(this::convertToQueryParameter)
            .reduce((parameter1, parameter2) -> String.format("%s&%s", parameter1, parameter2))
            .map(query -> "?" + query)
            .orElse("")
        ;
    }

    private String offset() {
        return String.valueOf((page - 1) * pageSize);
    }

    private String convertToQueryParameter(Map.Entry<String, String> parameter) {
        try {
            return String.format(
                "%s=%s",
                URLEncoder.encode(parameter.getKey(), "UTF-8"),
                URLEncoder.encode(parameter.getValue(), "UTF-8")
            );
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private QueryString(Map<String, String> parameters, int pageSize) {
        this.parameters = parameters;
        this.pageSize = pageSize;
    }
}
