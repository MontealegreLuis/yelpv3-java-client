/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.List;
import java.util.Map;

public class SearchResult {
    public final int total;
    public final Businesses businesses;
    public final Region region;

    public SearchResult(int total, Businesses businesses, Region region) {
        this.total = total;
        this.businesses = businesses;
        this.region = region;
    }

    /**
     * Sometimes it will be needed to convert a search result to JSON. Using a <code>Map</code> to
     * convert it to JSON later, is one way that requires few effort.
     * <p>
     * One common use case is to create a Google map for the businesses in the current search result
     * <p>
     * In this case it is possible to customize which properties to use. Let's suppose that only the
     * business ID, name, and coordinates are needed
     * <p>
     * <code>
     *     class BusinessMapper implements CustomBusinessesMapper {
     *         public List<Map<String,Object>> map(Businesses businesses) {
     *             return businesses
     *                 .stream()
     *                 .map(this::businessToMap)
     *                 .collect(Collectors.toList())
     *             ;
     *         }
     *
     *         private Map<String, Object> businessToMap(Business business) {
     *             Map<String, Object> businessInformation = new HashMap<>();
     *             businessInformation.put("id", business.id);
     *             businessInformation.put("name", business.name);
     *             businessInformation.put("coordinates", business.coordinates);
     *             return businessInformation;
     *         }
     *     }
     * </code>
     */
    public List<Map<String, Object>> businessesToMap(CustomBusinessesMapper mapper) {
        return mapper.map(businesses);
    }

    public interface CustomBusinessesMapper {
        List<Map<String,Object>> map(Businesses businesses);
    }
}
