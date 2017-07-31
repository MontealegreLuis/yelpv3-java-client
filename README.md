# YELP API V3 client

[![codebeat badge](https://codebeat.co/badges/6af99e8b-adb4-4f26-9fa8-e586aa7986e6)](https://codebeat.co/projects/github-com-montealegreluis-yelpv3-java-client-master)

This is a Java Client for [YELP Fusion](https://www.yelp.com/developers/documentation/v3)

It currently supports

* [Search](https://www.yelp.com/developers/documentation/v3/business_search)
* [Business](https://www.yelp.com/developers/documentation/v3/business)

# Installation

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
        <version>1.0.0</version>
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

Yelp's Business Search API supports two types of search

* **By Location**. It uses the combination of address, neighborhood, city, state or zip, 
  and optionally the country
* **By Coordinates**. The latitude and longitude of the business

They cannot be used at the same time

#### Search by location and coordinates

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");
        
        yelp.search(SearchCriteria.byLocation("San Antonio")).searchResult();
        yelp.search(SearchCriteria.byCoordinates(29.426786, -98.489576)).searchResult();
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
        
        SearchCriteria criteria = SearchCriteria
            .byLocation("San Antonio")
            .withTerm("restaurants")
            .withinARadiusOf(Distance.inMiles(2))
            .inCategories("mexican")
            .withPricing(PricingLevel.MODERATE)
            .withAttributes(Attribute.HOT_AND_NEW, Attribute.DEALS)
            .openNow()
            .limit(10)
            .offset(5)
            .sortBy(SortingMode.REVIEW_COUNT)
        ;
        
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
        for (BasicInformation business : result.businesses) {
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
                business.transactions.forEach(System.out::println);
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
        
        Business business = response.business();
       
        System.out.println(business.basicInformation.id);
        System.out.println(business.basicInformation.name);
        System.out.println(business.basicInformation.rating);
        System.out.println(business.basicInformation.distance);
        System.out.println("Location:");
        System.out.println(business.basicInformation.location.address1);
        System.out.println(business.basicInformation.location.zipCode);
        System.out.println("Coordinates:");
        System.out.println(business.basicInformation.coordinates.latitude);
        System.out.println(business.basicInformation.coordinates.longitude);
        System.out.println("Transactions");
        if (business.basicInformation.transactions.size() > 0)
            business.basicInformation.transactions.forEach(System.out::println);
        else System.out.println("No transactions for this business");
        System.out.println("Photos");
        for (String photo: business.details.photos) System.out.println(photo);
        if (business.details.schedule.isOpenNow) System.out.println("Business is open now!");
        for (Hour hours : business.details.schedule.hours) {
            System.out.println(hours.day.toString());
            System.out.println(hours.start.toString());
            System.out.println(hours.end.toString());
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
