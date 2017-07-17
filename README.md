# YELP API V3 client

[![codebeat badge](https://codebeat.co/badges/6af99e8b-adb4-4f26-9fa8-e586aa7986e6)](https://codebeat.co/projects/github-com-montealegreluis-yelpv3-java-client-master)

This is a Java Client for [YELP Fusion](https://www.yelp.com/developers/documentation/v3)

# Installation

You can use this library via Maven and [Jitpack](https://jitpack.io/). Add 
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
and renew it only if needed. All searches authenticate automatically if no access token
is present.

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

Currently you can search by location and by coordinates. It is also possible
to specify the amount of results returned by the search.

#### Search by location

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");
        
        List<Business> businesses = yelp.search(SearchCriteria.byLocation("San Antonio").limit(3));
        
        System.out.println(businesses.size());
        for (Business business : businesses) {
            System.out.println("--------------------------------------");
            System.out.println(business.id());
            System.out.println(business.name());
            System.out.println(business.rating());
            System.out.println(business.distance());
            System.out.println("Location:");
            System.out.println(business.location().address1());
            System.out.println(business.location().zipCode());
            System.out.println("Coordinates:");
            System.out.println(business.coordinates().latitude());
            System.out.println(business.coordinates().longitude());
            System.out.println("Transactions");
            if (business.transactions().size() > 0)
                business.transactions().forEach(System.out::println);
            else System.out.println("No transactions for this business");
        }
    }
}
```

#### Search by coordinates

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");
        
        List<Business> businesses = yelp.search(
            SearchCriteria.byCoordinates(29.426786, -98.489576).limit(3)
        );
        
        System.out.println(businesses.size());
        for (Business business : businesses) {
            System.out.println("--------------------------------------");
            System.out.println(business.id());
            System.out.println(business.name());
            System.out.println(business.rating());
            System.out.println(business.distance());
            System.out.println("Location:");
            System.out.println(business.location().address1());
            System.out.println(business.location().zipCode());
            System.out.println("Coordinates:");
            System.out.println(business.coordinates().latitude());
            System.out.println(business.coordinates().longitude());
            System.out.println("Transactions");
            if (business.transactions().size() > 0)
                business.transactions().forEach(System.out::println);
            else System.out.println("No transactions for this business");
        }
    }
}
```

#### Search by ID

```java
public class Demo {
    public static void main(String[] args){
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET");
        
        Business business = yelp.searchById("bella-on-the-river-san-antonio");
        
        System.out.println(business.id());
        System.out.println(business.name());
        System.out.println(business.rating());
        System.out.println(business.distance());
        System.out.println("Location:");
        System.out.println(business.location().address1());
        System.out.println(business.location().zipCode());
        System.out.println("Coordinates:");
        System.out.println(business.coordinates().latitude());
        System.out.println(business.coordinates().longitude());
        System.out.println("Transactions");
        if (business.transactions().size() > 0)
            business.transactions().forEach(System.out::println);
        else System.out.println("No transactions for this business");
    }
}
```

#### Using an existing token

As tokens expire every 180 days. It is possible to store the token and the expiration date
in a database and use it, instead of getting a new token every time.

```java
public class Demo {
    public static void main(String[] args){
        AccessToken token = AccessToken.fromValues("EXISTING_TOKEN", "TOKEN_TYPE", 15552000);
        
        Yelp yelp = new Yelp("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET", token);
        
        Business business = yelp.searchById("bella-on-the-river-san-antonio");
    }
}
```
