/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.reviews;

import java.net.URL;
import java.util.Date;

public class Review {
    public final int rating;
    public final User user;
    public final String text;
    public final Date createdAt;
    public final URL url;

    public Review(int rating, User user, String text, Date createdAt, URL url) {
        this.rating = rating;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
        this.url = url;
    }
}
