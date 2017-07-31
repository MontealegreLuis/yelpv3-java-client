/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.businesses;

public class Business {
    public final BasicInformation basicInformation;
    public final Details details;

    public Business(BasicInformation basicInformation, Details details) {
        this.basicInformation = basicInformation;
        this.details = details;
    }

    public boolean isOpenNow() {
        return details.schedule.isOpenNow;
    }
}
