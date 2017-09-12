/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.jsonparser;

import com.montealegreluis.yelpv3.businesses.Business;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BusinessParserTest {
    @Test
    public void it_sets_the_image_url_null_if_it_is_empty() {
        String businessJson = "{\n" +
            "  \"distance\": 19366.343726119998,\n" +
            "  \"image_url\": \"\",\n" +
            "  \"rating\": 5,\n" +
            "  \"coordinates\": {\n" +
            "    \"latitude\": 29.51485,\n" +
            "    \"longitude\": -98.29814\n" +
            "  },\n" +
            "  \"review_count\": 1,\n" +
            "  \"transactions\": [],\n" +
            "  \"url\": \"https://www.yelp.com/biz/mi-taco-2-converse?adjust_creative=IURx5a__O8WleFIW8SaaBg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=IURx5a__O8WleFIW8SaaBg\",\n" +
            "  \"display_phone\": \"\",\n" +
            "  \"phone\": \"\",\n" +
            "  \"name\": \"Mi Taco #2\",\n" +
            "  \"location\": {\n" +
            "    \"country\": \"US\",\n" +
            "    \"address3\": \"\",\n" +
            "    \"address2\": \"\",\n" +
            "    \"city\": \"Converse\",\n" +
            "    \"address1\": \"\",\n" +
            "    \"display_address\": [\"Converse, TX 78109\"],\n" +
            "    \"state\": \"TX\",\n" +
            "    \"zip_code\": \"78109\"\n" +
            "  },\n" +
            "  \"id\": \"mi-taco-2-converse\",\n" +
            "  \"categories\": [\n" +
            "    {\n" +
            "      \"alias\": \"mexican\",\n" +
            "      \"title\": \"Mexican\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"alias\": \"foodtrucks\",\n" +
            "      \"title\": \"Food Trucks\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"is_closed\": false\n" +
            "}";

        Business business = BusinessParser.businessFrom(new JSONObject(businessJson));

        assertThat(business.image, is(nullValue()));
    }

    @Test
    public void it_handles_a_null_address1_value() {
        String businessJson = "{\n" +
            "  \"distance\": 898.255374076,\n" +
            "  \"image_url\": \"https://s3-media1.fl.yelpcdn.com/bphoto/GgcpgqwQG1eTNdw6mXD51Q/o.jpg\",\n" +
            "  \"rating\": 5,\n" +
            "  \"coordinates\": {\n" +
            "    \"latitude\": 29.5412464141846,\n" +
            "    \"longitude\": -98.5188369750977\n" +
            "  },\n" +
            "  \"review_count\": 8,\n" +
            "  \"transactions\": [],\n" +
            "  \"url\": \"https://www.yelp.com/biz/wicked-dogs-san-antonio?adjust_creative=IURx5a__O8WleFIW8SaaBg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=IURx5a__O8WleFIW8SaaBg\",\n" +
            "  \"display_phone\": \"(210) 426-9750\",\n" +
            "  \"phone\": \"+12104269750\",\n" +
            "  \"price\": \"$\",\n" +
            "  \"name\": \"Wicked Dogs\",\n" +
            "  \"location\": {\n" +
            "    \"country\": \"US\",\n" +
            "    \"address3\": \"\",\n" +
            "    \"address2\": null,\n" +
            "    \"city\": \"San Antonio\",\n" +
            "    \"address1\": null,\n" +
            "    \"display_address\": [\"San Antonio, TX 78213\"],\n" +
            "    \"state\": \"TX\",\n" +
            "    \"zip_code\": \"78213\"\n" +
            "  },\n" +
            "  \"id\": \"wicked-dogs-san-antonio\",\n" +
            "  \"categories\": [\n" +
            "    {\n" +
            "      \"alias\": \"foodtrucks\",\n" +
            "      \"title\": \"Food Trucks\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"alias\": \"hotdog\",\n" +
            "      \"title\": \"Hot Dogs\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"is_closed\": false\n" +
            "}]";

        Business business = BusinessParser.businessFrom(new JSONObject(businessJson));

        assertThat(business.location.address1, is(nullValue()));
    }

}
