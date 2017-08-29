/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.Coordinates;
import com.montealegreluis.yelpv3.businesses.PricingLevel;
import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SearchCriteria {
    private final int defaultPageSize = 20;
    private Map<String, String> parameters = new HashMap<>();

    /**
     * <code>location</code> is the combination of:
     * <p>
     * Address, neighborhood, city, state or zip, optional country to be used when searching for
     * businesses.
     */
    public static SearchCriteria byLocation(String location) {
        return new SearchCriteria(location);
    }

    public static SearchCriteria byCoordinates(Coordinates coordinates) {
        return new SearchCriteria(coordinates.latitude, coordinates.longitude);
    }

    public static SearchCriteria byCoordinates(Double latitude, Double longitude) {
        return new SearchCriteria(latitude, longitude);
    }

    /**
     * Number of business results to return. Default value is 20. Maximum is 50
     */
    public void limit(Limit limit) {
        parameters.put("limit", limit.value().toString());
    }

    /**
     * Maximum offset is 1, 000
     */
    public void offset(Offset offset) {
        parameters.put("offset", offset.value().toString());
    }

    /**
     * Sort the results by one of the values in <code>SortingMode</code>
     * <p>
     * Default values is <code>SortingMode.BEST_MATCH</code>.
     * <p>
     * The rating sort is not strictly sorted by the rating value, but by an adjusted rating value
     * that takes into account the number of ratings, similar to a bayesian average. This is so a
     * business with 1 rating of 5 stars doesn’t immediately jump to the top.
     */
    public void sortBy(SortingMode mode) {
        parameters.put("sort_by", mode.toString());
    }

    /**
     * Search term, e.g. "food", "restaurants"
     * <p>
     * The term keyword also accepts business names such as "Starbucks".
     */
    public void withTerm(String term) {
        parameters.put("term", term);
    }

    /**
     * Search radius in meters, kilometers or miles.
     * <p>
     * The max value is 40000 meters (40 kilometers or 25 miles).
     *
     * @see Radius
     */
    public void withinARadiusOf(Radius radius) {
        parameters.put("radius", radius.toMeters());
    }

    /**
     * Only return the businesses open now
     *
     * @throws IncompatibleCriteria When used in combination with <code>openAt</code>
     */
    public void openNow() {
        if (parameters.containsKey("open_at"))
            throw IncompatibleCriteria.mixing("open_at", "open_now");

        parameters.put("open_now", Boolean.toString(true));
    }

    /**
     * @param categories Comma separated list of categories
     * @see SearchCategories
     * @link https://www.yelp.com/developers/documentation/v2/all_category_list
     */
    public void inCategories(String categories) {
        parameters.put("categories", categories);
    }

    /**
     * If several levels are provided, for example, <code>INEXPENSIVE, MODERATE, PRICEY</code> it
     * will filter the results to show the ones that are $, $$, OR $$$.
     */
    public void withPricing(PricingLevel... levels) {
        StringBuilder prices = new StringBuilder();
        for (PricingLevel level : levels) prices.append(level.value()).append(",");
        parameters.put("price", prices.substring(0, prices.length() - 1));
    }

    /**
     * If multiple attributes are used, only businesses that satisfy ALL attributes will be returned
     * in search results.
     * <p>
     * For example, the attributes <code>hot_and_new,cashback</code> will return businesses that are
     * Hot and New AND offer Cash Back.
     */
    public void withAttributes(Attribute... attributes) {
        StringBuilder filters = new StringBuilder();
        for (Attribute attribute : attributes) filters.append(attribute.value()).append(",");
        parameters.put("attributes", filters.substring(0, filters.length() - 1));
    }

    /**
     * @throws IncompatibleCriteria When used in combination with <code>openNow</code>
     * @param timestamp Unix timestamp in the same timezone of the search location
     */
    public void openAt(Long timestamp) {
        if (parameters.containsKey("open_now"))
            throw IncompatibleCriteria.mixing("open_now", "open_at");

        parameters.put("open_at", timestamp.toString());
    }

    /**
     * See the documentation for the full list of supported locales
     *
     * @link https://www.yelp.com/developers/documentation/v3/supported_locales
     */
    public void withLocale(Locale locale) {
        parameters.put("locale", locale.toString());
    }

    public void addQueryParametersTo(URIBuilder builder) {
        parameters.forEach(builder::setParameter);
    }

    public Pagination pagination(int total) {
        return Pagination.fromSearch(this, total);
    }

    public QueryString toQueryString() {
        return QueryString.build(parameters, limit());
    }

    /**
     * If a limit is not specified it will return the default page size (20)
     */
    public int limit() {
        return parameters.containsKey("limit")
            ? Integer.valueOf(parameters.get("limit"))
            : defaultPageSize
        ;
    }

    /**
     * If an offset is not specified it will return the default offset (0)
     */
    public int offset() {
        return parameters.containsKey("offset")
            ? Integer.valueOf(parameters.get("offset"))
            : 0
        ;
    }

    private SearchCriteria(Double latitude, Double longitude) {
        parameters.put("latitude", latitude.toString());
        parameters.put("longitude", longitude.toString());
    }

    private SearchCriteria(String location) {
        parameters.put("location", location);
    }

    @Override
    public String toString() {
        return parameters.toString();
    }
}
