# YELP API V3 client

[![Build Status](https://travis-ci.org/MontealegreLuis/yelpv3-java-client.svg?branch=master)](https://travis-ci.org/MontealegreLuis/yelpv3-java-client)
[![codebeat badge](https://codebeat.co/badges/6af99e8b-adb4-4f26-9fa8-e586aa7986e6)](https://codebeat.co/projects/github-com-montealegreluis-yelpv3-java-client-master)

This is a Java Client for [YELP Fusion](https://www.yelp.com/developers/documentation/v3)

It currently supports the following endpoints

* [Search](https://www.yelp.com/developers/documentation/v3/business_search)
* [Business](https://www.yelp.com/developers/documentation/v3/business)
* [Reviews](https://www.yelp.com/developers/documentation/v3/business_reviews)

1. [Installation](#installation)
1. [Usage](#usage)
    1. [Authentication](#authentication)
    1. [Searching businesses](#searching-businesses)
        1. [Search by location and coordinates](#search-by-location-and-coordinates)
        1. [Searching options](#searching-options)
        1. [Using this library as a proxy](#using-this-library-as-a-proxy)
        1. [De-serializing the response](#de-serializing-the-response)
    1. [Searching by ID](#searching-by-id)
    1. [Business reviews](#business-reviews)
    1. [Using an existing token](#using-an-existing-token)
    1. [Working with Yelp's categories](#working-with-yelps-categories)
1. [Tests](#tests)
1. [LICENSE](#license)

## Installation

Install this library via Maven and [Jitpack](https://jitpack.io/). Add
the following repository to your POM file.

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Now add the following dependency.

```xml
<dependencies>
    <dependency>
        <groupId>com.github.MontealegreLuis</groupId>
        <artifactId>yelpv3-java-client</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
```

That's it! Enjoy!

## Usage

### Authentication

Since access tokens expire in 180 days, you could save the token in your database,
and renew it only if needed. All methods will authenticate automatically if no
access token is present.

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        AccessToken token = yelp.token();

        System.out.printf("Access token:%s%n", token.accessToken());
        System.out.printf("Token type:%s%n", token.tokenType());
        System.out.printf("Expires on:%s%n", token.expiresOn());
        System.out.printf("Is it expired? %s%n", token.isExpired());
    }
}
```

### Searching businesses

Yelp's Business Search API supports two types of searches

* **By Location**. It uses the combination of address, neighborhood, city, state or zip,
  and optionally the country
* **By Coordinates**. The latitude and longitude of the business

They cannot be used at the same time

#### Search by location and coordinates

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        yelp.search(SearchCriteria.byLocation("San Antonio"));
        yelp.search(SearchCriteria.byCoordinates(29.426786, -98.489576));
        yelp.search(SearchCriteria.byCoordinates(new Coordinates(29.426786, -98.489576)));
    }
}
```

#### Searching options

See the official 
[documentation](https://www.yelp.com/developers/documentation/v3/business_search) for more details.

```java
public class Demo {
    public static void main(String[] args) {
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");
        criteria.withTerm("restaurants");
        criteria.withinARadiusOf(Distance.inMiles(2));
        criteria.inCategories("mexican");
        criteria.withPricing(PricingLevel.MODERATE);
        criteria.withAttributes(Attribute.HOT_AND_NEW, Attribute.DEALS);
        criteria.openNow();
        criteria.limit(10);
        criteria.offset(5);
        criteria.sortBy(SortingMode.REVIEW_COUNT);

        yelp.search(criteria);
    }
}
```

It is possible to specify the same criteria when using coordinates instead of location.

#### Using this library as a proxy

If you want to use this library as a proxy to avoid CORS issues in your JavaScript. It is possible
to get the original Yelp JSON response.

```java
public class Demo {
    public static void main(String[] args) {
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");

        String originalJsonResponse = yelp.search(criteria).originalResponse();
    }
}
```

#### De-serializing the response

This library comes with a default de-serializer, tha will convert the original JSON response into a
DTO.

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        SearchCriteria criteria = SearchCriteria.byLocation("San Antonio");

        SearchResult result = yelp.search(criteria).searchResult();

        System.out.println(result.total);
        for (Business business : result.businesses) {
            System.out.println("--------------------------------------");
            System.out.println(business.id);
            System.out.println(business.name);
            System.out.println(business.rating);
            System.out.println(business.distance.toString());
            System.out.println("Location:");
            System.out.println(business.location.address1);
            System.out.println(business.location.zipCode);
            System.out.println("Coordinates:");
            System.out.println(business.coordinates.latitude);
            System.out.println(business.coordinates.longitude);
            System.out.println("Transactions");
            if (business.transactions.size() > 0)
                business.transactions.forEach(transaction -> System.out.println(transaction.label));
            else System.out.println("No transactions were registered for this business");
        }
    }
}
```

### Searching by ID

We also have 2 options when searching by ID

* Get the original response
* De-serialize the response to a DTO

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        BusinessResponse response = yelp.searchById("bella-on-the-river-san-antonio");

        String jsonResponse = response.originalResponse();

        BusinessDetails business = response.business();

        System.out.println(business.id);
        System.out.println(business.name);
        System.out.println(business.rating);
        System.out.println(business.distance);
        System.out.println("Location:");
        System.out.println(business.location.address1);
        System.out.println(business.location.zipCode);
        System.out.println("Coordinates:");
        System.out.println(business.coordinates.latitude);
        System.out.println(business.coordinates.longitude);
        System.out.println("Transactions");
        if (business.transactions.size() > 0)
            business.transactions.forEach(transaction -> System.out.println(transaction.label));
        else System.out.println("No transactions for this business");
        System.out.println("Photos");
        for (String photo: business.photos) System.out.println(photo);
        if (business.schedule.isOpenNow) System.out.println("Business is open now!");
        business.schedule.hours.forEach((day, hours) -> {
            System.out.println(hour.day.toString());
            for (Hour hour : hours) {
                System.out.println(hour.start.toString());
                System.out.println(hour.end.toString());
            }
        });
    }
}
```

### Business reviews

We also have 2 options when getting a business reviews

* Get the original response
* De-serialize the response to a DTO

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");

        BusinessResponse response = yelp.reviews("bella-on-the-river-san-antonio");

        String jsonResponse = response.originalResponse();

        List<Review> reviews = response.reviews();
        
        for (Review review: reviews) {
            System.out.println(review.createdAt);
            System.out.println(review.rating);
            System.out.println(review.text);
            System.out.println(review.user.image);
            System.out.println(review.user.name);
        }
    }
}
```

### Using an existing token

As tokens expire every 180 days. It is possible to store the token and the expiration date
in a database and use it, instead of getting a new token every time.

```java
public class Demo {
    public static void main(String[] args){
        String token;    // It should come from your database
        String type;     // It should come from your database
        long expiresOn;  // It should come from your database

        AccessToken token = AccessToken.fromValues(token, type, expiresOn);

        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET", token);

        Business business = yelp.searchById("bella-on-the-river-san-antonio");
    }
}
```

### Working with Yelp's categories

Currently, there's no endpoint to retrieve all the available categories in Yelp. However they offer
[this file](https://www.yelp.com/developers/documentation/v2/all_category_list/categories.json)
to download them in JSON format.

You can use the classes `SearchCategoriesParser` and `SearchCategories` to load and filter 
categories using a local copy of this file.

```java
public class Demo {
    public static void main(String[] args){
        SearchCategories categories = SearchCategoriesParser.all();
        
        SearchCategories parentCategoriesAvailableInTheUS = categories
            .parentCategories()
            .availableAt(Locale.US)
        ;

        SearchCategories childrenOfRestaurantsAvailableInMX = categories
            .childrenOf("restaurants")
            .availableAt(new Locale("es", "MX"))
        ;
    }
}
```

## Tests

To run the test suite, you'll need to create an `application.properties` file. You will need both, 
your Yelp API client and secret. This library has some integration tests to verify that when a
specific locale is requested, messages are translated to the correct language. You'll need a key
from the [Language detection API](https://detectlanguage.com/users/sign_up) in order to run those
tests.

Create a copy from `example.properties` and use your keys as values

```
$ cp src/main/resources/example.properties src/main/resources/application.properties
```

Run the tests suite using Maven

```
$ mvn test
```

## License

Released under the [MIT License](LICENSE)
