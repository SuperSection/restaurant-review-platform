# Restaurant Review Platform

A web-based platform that enables users to discover local restaurants, read authentic review from other diners, and share their own dining experiences through detailed reviews and rating, helping others make informed decision about where to eat.

## Definitions

### Restaurant Listing

A dedicated page or entry containing essential information about a restaurant including its name, location, cuisine type, operating hours and contact details.

### Review

A user-submitter assessment of their dining experience at a specific restaurant, including detailed written feedback and a numerical rating.

### Rating

A numerical score, typically on a scale od 1-5 stars, that represents a user's overall satisfaction with their experience at a restaurant.

---

## User Stories

### **View Restaurant Details**

As a potential diner, I want to see detailed information about restaurants in my area. So that I can make an informed decision about where to eat.

Acceptance Criteria

1. Restaurant listing display name, address, cuisine type, and operating hours
2. Users can view the restaurant's average rating
3. Restaurant contact information are displayed when available
4. Photos of the restaurant are displayed when available
5. Users can see the restaurant's location on a map

### **Read Restaurant Review**

As a potential diner, I want to read reviews from other customers. So that I can learn about others's experience before visiting.

Acceptance Criteria

1. Reviews are displayed in chronological order
2. Each review shows the author's name and date posted
3. Reviews display bot written feedback and numerical rating
4. Users can sort reviews by rating or date
5. The total number of reviews is clearly visible.

### **Submit Restaurant Review**

As a restaurant customer, I want to share my dining experience through a review. So that I can help others make informed dining decisions.

Acceptance Criteria

1. Users must be logged in to submit a review
2. Review form includes a text area for written feedback
3. Users can select a rating from 1-5 stars
4. Users can submit photos with their review
5. Users cannot submit multiple reviews for the same restaurant
6. Users can edit their review within 48 hours of posting

---

## Bonus Features

### **Enhanced Review Functionality**

As a reviewer, I want to rate specific aspects of my dining experience. So that I can provide more detailed feedback.

Acceptance Criteria

1. Users can rate food quality, service, ambiance, and value separately
2. Each aspect has its own 1-5 star rating
3. Users can add tags to their review (e.g. "great for dates", "family-friendly")
4. The platform calculates and displays average ratings for each aspect.

---

## Domain Overview

[Entities](#create-all-the-required-entities) we need:

- User
- Restaurant
- Review
- Photo
- Address
  - Geolocation
- OperatingHours

### ER Diagram

![Domain Overview Diagram](./diagram/domains.png)

- The `User` can create restaurants and write.
- Each `Restaurant` has core details, rating, photos, and nested structures.
- A `Review` includes content, 1-5 star rating, and photos within 48hr edit window.
- Supporting objects like `Address` and `OperatingHours` help organize data.

### REST API Overview

- The REST API allows for managing Restaurants, Reviews, and Photos.

---

## UI Overview

- Homepage displays resturant cards with images, ratings, and cuisine types
- Restaurant details page shows location map, reviews, and submission form.
- Search functionality supports seach and filtering by rating.
- Users can submit reviews with ratings, text, and photos.
- Restaurant owners can add new establishments via a dedicated form.

## Project Architecture Overview

![Project Architecture](./diagram/project-architecture.png)

- [Frontend](#frontend-setup) uses Next.js to handle UI and client-side interactions.
- [Backend](#spring-boot-project-setup-steps) uses **Spring Boot** for business logic and API endpoints.
- **Elasticsearch** stores data and enables advanced search features.
- **Keycloak** manages authentication using OAuth2 and OpenID Connect.

### Project In Brief

- Platform enables restaurant discovery and reviews
- Implements core features: search, [auth](#authentication-setup-overview), and image handling
- Focuses on advanced search functionality
- Includes geospatial capailities

---

## Spring Boot Project Setup Steps

1. [Create a new Spring Boot project](#project-structure)
2. [Setting up Elasticsearch with Docker](#docker-compose-setup)
3. [Installing and configuring Kibana](#elasticsearch--kibana)
4. [Setting up Keycloak with Docker](#keycloak)
5. [Configure Mapstruct with Lombok](#mapstruct-setup)
6. [Running the frontend application](#frontend-setup)

---

## Project Structure

A new Spring Boot Project is created using [spring-initialzr](https://start.spring.io/)

- Project follows standard **Spring Boot** directory structure
- Selected *Java 21*, Maven and Jar packaging format
- Spring Boot version: `3.5.0`
- `pom.xml` contains our selected dependencies:
  - Spring Web
  - Spring Security
  - OAuth2 Resource Server
  - Spring Data Elasticsearch (Access+Driver)
  - Validation
  - Lombok
- Main application class serves as the entry point
- Resource files are stored in `src/main/resources`

## Docker Compose Setup

- Docker Compose configured to run Elasticsearch and Kibana locally
- Elasticsearch runs on [port 9200](http://localhost:9200), Kibana on [port 5601](http://localhost:5601)
- Application configured to connect Elasticsearch
- Data persists through container restarts using Docker volumes

### **Elasticsearch & Kibana**

- An **index** is a collection of documents uniquely identified by a name or an alias.
- Each document is a collection of fields, which each have their own data type.
- Kibana's **Index Management** features are an easy, convenient way to manage your cluster’s indices, data streams, index templates, and enrich policies.

  - <http://localhost:5601/app/home#/> > Management > Stack Management > Index Management
  - Explore available index actions & management features.

---

## Keycloak

Keycloak is an open-source Identity and Access Management (IAM) tool, developed by Red Hat, provides a centralized solution for user authentication and authorization across multiple applications.

Keycloak supports different protocols like SSO, OAuth2, OpenID Connect, LDAP, and more.

> **Single Sign-On** (SSO): Enables users to log in once and access multiple applications and services without needing to re-enter credentials.

### **Keycloak Setup**

- Add [Keycloak](https://quay.io/repository/keycloak/keycloak) service configuration to Docker Compose.
- Expose Keycloak's Administration Console on [port 9090](http://localhost:9090)
- Sign In (using admin user & password)
- `Manage realms > Create realm`
- Realm name: `restaurant-review`, then Create
- Configure Spring Boot to use Keycloak for JWT validation (in `application.properties`)

    ```properties
    spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/restaurant-review
    ```

  This configuration is used to secure REST APIs using OAuth2 and JWT (JSON Web Tokens), with Keycloak acting as the Identity Provider (IdP).

  1. **Resource Server**: This Spring Boot app is acting as a resource server, meaning it serves protected APIs that require a valid access token.

  2. **JWT Support**: It expects JWT tokens (access tokens) in the Authorization header (`Bearer <token>`).

  3. **Issuer URI**: Spring queries the following well-known endpoint (automatically):

      ```bash
      http://localhost:9090/realms/restaurant-review/.well-known/openid-configuration
      ```

      to get all metadata (like public keys, token validation rules, etc.) from the Keycloak server.

---

### **Mapstruct Setup**

- `<properties>` to be compatible with each other (in `pom.xml` file):

    ```xml
    <lombok.version>1.18.36</lombok.version>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
    ```

- Add Mapstruct dependency

    ```xml
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.6.3</version>
    </dependency>
    ```

- Configure Maven Compiler Plugin for annotation processing
- Setup Lombok-Mapstruct binding for compatibility

    ```xml
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <configuration>
        <annotationProcessorPaths>
          <!-- Lombok must come first -->
          <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
          </path>
          <!-- Mapstruct processor -->
          <path>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${org.mapstruct.version}</version>
          </path>
          <!-- Mapstruct binding -->
          <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok-mapstruct-binding</artifactId>
            <version>0.2.0</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>
    ```

### **Frontend Setup**

- Download / clone the frontend from source code
- Install the dependencies with `npm install`
- Run the frontend project with `npm run dev`

---

## Create all the required Entities

1. Create the User entity
2. Create the Address address
3. Create the OperatingHours entity
4. Create the Photo entity
5. Create the Review entity
6. Create the Restaurant entity

> Used Lombok annotations to reduce boilerplate code.

### **Use of Elasticsearch annotations in Entities**

- Configure `@Field` annotations for Elasticsearch
- Used `FieldType.Keyword` for exact matching of IDs (or exact-match fields like `postalCode`, `url`)
- Used `FieldType.Text` for searchable name fields (or searchable fields like `streetName`)
- Built the `OperatingHours` entity containing nested `TimeRange` objects.
- Used `FieldType.Nested` to maintain relationships between fields.
- Configured `LocalDateTime` with proper date format.
- Added timestamp fields with proper `DateFormat` config for `Review` entity.
- Used `FieldType.Integer` for the `rating` field.
- Created `Restaurant` entity with `@Document` configuration for Elasticsearch.
- Integrated `@GeoPointField` for storing restaurant location data.

> Initialized the `photos` & `reviews` list for safe handling

### Create Restaurant Repository

- Created the `RestaurantRepository` interface extending `ElasticsearchRepository`
- Added `@Repository` annotation for Spring configuration
- Gained access to basic CRUD operations through **inheritance**.

---

## Authentication Setup Overview

1. Create users in Keycloak
2. Configure OAuth2 resource server

### Author

- [Soumo Sarkar](https://linkedin.com/in/soumo-sarkar)
