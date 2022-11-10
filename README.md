# Explore Learning Challenge

## Deliverables

* Please file on GitHub or google drive and share it with me.

Using

* Spring Boot
* Embedded Tomcat

## Requirements

* A REST controller with individual services for:
    * Adds a User to the list of stored users using a JSON request body.
        * The request should be rejected if the same first name/last name combination is already stored in the system.
    * Returns a single User when requested by ID as JSON
    * Returns a list of all Users as JSON, ordered alphabetically by last name
    * Deletes a User when requested by ID
* User object includes ID, first name, and, last name
* Store user using the h2 embedded database.
* Protect each endpoint with Spring Security.
* Appropriate HTTP status codes
* Mocks and Unit Test

## Running

```
./mvnw spring-boot:run
```

## Endpoints

* `POST /users` create a user with following request body JSON structure:
    * ```
        {
            "firstName": String,
            "lastName": String
        }
      ```
    * first name / last name combination must be unique
* `GET /users` get all users, ordered alphabetically by last name
* `GET /users/:id` get a user by id path parameter
* `DELETE /users/:id` delete a user by id path parameter

Endpoints require an API key (passed with header `X-API-KEY`). The key for this exercise is `113`.

## Curl Examples

```
curl -H "X-API-KEY: 113" -X POST http://localhost:8080/v1/users -H 'Content-Type: application/json' -d '{"firstName":"John","lastName":"Guest"}'
curl -H "X-API-KEY: 113" http://localhost:8080/v1/users
curl -H "X-API-KEY: 113" http://localhost:8080/v1/users/1
curl -X DELETE -H "X-API-KEY: 113" http://localhost:8080/v1/users/1
```

## Design Considerations

* Code organization
  * I chose `user` and `security` packages due to the small nature of this exercise, rather than many packages with one class each (e.g. `controller`, `repository`, etc.)
  * For readability and conciseness, I chose to use a `controller -> repository -> entity` pattern, rather than introduce service / dao layers. 
* Unit testing
  * I am personally somewhat new to `@WebMvcTest`, but I found it to provide adequate business logic test coverage, while still allowing me to test http requests and responses.
  * Traditionally, I would have had separate unit and integration tests.