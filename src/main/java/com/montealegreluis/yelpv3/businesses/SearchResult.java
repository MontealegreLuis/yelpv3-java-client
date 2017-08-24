/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SearchResult {
    public final int total;
    public final List<Business> businesses;
    public final Region region;

    public SearchResult(int total, List<Business> businesses, Region region) {
        this.total = total;
        this.businesses = Collections.unmodifiableList(businesses);
        this.region = region;
    }

    public List<Map<String, Object>> businessesToMap(CustomBusinessesMapper mapper) {
        return mapper.map(businesses);
    }

    public interface CustomBusinessesMapper {
        List<Map<String,Object>> map(List<Business> businesses);
    }
}
