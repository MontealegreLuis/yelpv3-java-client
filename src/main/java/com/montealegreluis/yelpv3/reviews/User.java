/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.reviews;

import java.net.URL;

public class User {
    public final URL image;
    public final String name;

    public User(URL image, String name) {
        this.image = image;
        this.name = name;
    }
}
