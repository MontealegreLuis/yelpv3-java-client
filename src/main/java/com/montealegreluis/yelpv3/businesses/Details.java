/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

import java.util.Collections;
import java.util.List;

public class Details {
    public final boolean isClaimed;
    public final List<String> photos;
    public final Schedule schedule;

    public Details(boolean isClaimed, List<String> photos, Schedule schedule) {
        this.isClaimed = isClaimed;
        this.photos = Collections.unmodifiableList(photos);
        this.schedule = schedule;
    }
}
