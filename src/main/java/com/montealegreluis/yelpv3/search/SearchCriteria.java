/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

import com.montealegreluis.yelpv3.businesses.Coordinates;
import com.montealegreluis.yelpv3.businesses.PricingLevel;
import com.montealegreluis.yelpv3.businesses.distance.Distance;
import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.montealegreluis.yelpv3.businesses.distance.UnitOfLength.METERS;

public class SearchCriteria {
    private final int defaultPageSize = 20;
    private Map<String, String> parameters = new HashMap<>();

    public static SearchCriteria byLocation(String location) {
        return new SearchCriteria(location);
    }

    public static SearchCriteria byCoordinates(Coordinates coordinates) {
        return new SearchCriteria(coordinates.latitude, coordinates.longitude);
    }

    public static SearchCriteria byCoordinates(Double latitude, Double longitude) {
        return new SearchCriteria(latitude, longitude);
    }

    public void limit(Integer limit) {
        if (limit > 50) throw TooManyResults.requested(limit);

        parameters.put("limit", limit.toString());
    }

    public void offset(Integer offset) {
        parameters.put("offset", offset.toString());
    }

    public void sortBy(SortingMode mode) {
        parameters.put("sort_by", mode.toString());
    }

    public void withTerm(String term) {
        parameters.put("term", term);
    }

    public void withinARadiusOf(Distance distance) {
        if (distance.biggerThan(Distance.largest())) throw AreaTooLarge.withADistanceOf(distance);

        parameters.put(
            "radius",
            (Integer.valueOf(distance.convertTo(METERS).value.intValue())).toString()
        );
    }

    public void openNow() {
        if (parameters.containsKey("open_at"))
            throw IncompatibleCriteria.mixing("open_at", "open_now");

        parameters.put("open_now", Boolean.toString(true));
    }

    /**
     * @param categories Comma separated list of categories
     *                   See https://www.yelp.com/developers/documentation/v2/all_category_list
     */
    public void inCategories(String categories) {
        parameters.put("categories", categories);
    }

    public void withPricing(PricingLevel level) {
        parameters.put("price", level.value().toString());
    }

    public void withAttributes(Attribute... attributes) {
        StringBuilder filters = new StringBuilder();
        for (Attribute attribute : attributes) filters.append(attribute.value()).append(",");
        parameters.put("attributes", filters.substring(0, filters.length() - 1));
    }

    public void openAt(Long timestamp) {
        if (parameters.containsKey("open_now"))
            throw IncompatibleCriteria.mixing("open_now", "open_at");

        parameters.put("open_at", timestamp.toString());
    }

    /**
     * See https://www.yelp.com/developers/documentation/v3/supported_locales for the full list
     * of supported locales
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

    public int limit() {
        return parameters.containsKey("limit")
            ? Integer.valueOf(parameters.get("limit"))
            : defaultPageSize
        ;
    }

    public int offset() {
        return parameters.containsKey("offset")
            ? Integer.valueOf(parameters.get("offset"))
            : 0
        ;
    }

    SearchCriteria(SearchCriteria another) {
        parameters = new HashMap<>(another.parameters);
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
