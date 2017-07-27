/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3;

import com.montealegreluis.yelpv3.businesses.PricingLevel;
import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SearchCriteria {
    private Map<String, String> parameters = new HashMap<>();

    public static SearchCriteria byLocation(String location) {
        return new SearchCriteria(location);
    }

    public static SearchCriteria byCoordinates(Double latitude, Double longitude) {
        return new SearchCriteria(latitude, longitude);
    }

    public SearchCriteria limit(Integer limit) {
        if (limit > 50) throw TooManyResults.requested(limit);

        parameters.put("limit", limit.toString());
        return this;
    }

    public SearchCriteria offset(Integer offset) {
        parameters.put("offset", offset.toString());
        return this;
    }

    public SearchCriteria sortBy(SortingMode mode) {
        parameters.put("sort_by", mode.toString());
        return this;
    }

    public SearchCriteria withTerm(String term) {
        parameters.put("term", term);
        return this;
    }

    public SearchCriteria withinARadiusOf(Integer meters) {
        if (meters > 40000) throw AreaTooLarge.withAMeasureOf(meters);

        parameters.put("radius", meters.toString());
        return this;
    }

    public SearchCriteria onlyOpenBusinesses() {
        if (parameters.containsKey("open_at"))
            throw IncompatibleCriteria.mixing("open_at", "open_now");

        parameters.put("open_now", Boolean.toString(true));
        return this;
    }

    /**
     * @param categories Comma separated list of categories
     *                   See https://www.yelp.com/developers/documentation/v2/all_category_list
     */
    public SearchCriteria inCategories(String categories) {
        parameters.put("categories", categories);
        return this;
    }

    public SearchCriteria withPricing(PricingLevel level) {
        parameters.put("price", level.value().toString());
        return this;
    }

    public SearchCriteria withAttributes(Attribute... attributes) {
        StringBuilder filters = new StringBuilder();
        for (Attribute attribute : attributes) filters.append(attribute.value()).append(",");
        parameters.put("attributes", filters.substring(0, filters.length() - 1));
        return this;
    }

    public SearchCriteria openAt(Long timestamp) {
        if (parameters.containsKey("open_now"))
            throw IncompatibleCriteria.mixing("open_now", "open_at");

        parameters.put("open_at", timestamp.toString());
        return this;
    }

    void addQueryParametersTo(URIBuilder builder) {
        parameters.forEach(builder::setParameter);
    }

    private SearchCriteria(Double latitude, Double longitude) {
        parameters.put("latitude", latitude.toString());
        parameters.put("longitude", longitude.toString());
    }

    private SearchCriteria(String location) {
        parameters.put("location", location);
    }

    /**
     * See https://www.yelp.com/developers/documentation/v3/supported_locales for the full list
     * of supported locales
     */
    public SearchCriteria withLocale(Locale locale) {
        parameters.put("locale", locale.toString());
        return this;
    }

    @Override
    public String toString() {
        return parameters.toString();
    }
}
