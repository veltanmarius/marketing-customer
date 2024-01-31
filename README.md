# marketing-customer
Customer data (backend part) service that contains minimal operations

## Business requirements

### Customer Data Model
- id
- first name
- last name
- age
- email address
- current living address:
  - street name
  - house number
  - postal code
  - city
  - country

Mandatory fields: first name, last name, email or address (all fields)
Constraints: age > 18 and above.
Technical limitation for other fields: not set.

### Exposed REST API
- Add
- Retrieve all
- Retrieve by id
- Update can be only for address and email
- No delete
- Search by first name or last name

### Tech stack
- Java 17 or above
- Spring Boot 3.x
- Build system: maven or gradle
- Docker container
- CI/CD pipeline
- Well design production ready application
- Persistence in an SQL DB (in memory DB)
- Maximum 10k customers.

## API Documentation
The endpoints exposed are documented using Open API library

* UI path: $HOST:$PORT/openapi/swagger-ui.html
* API Docs path: $HOST:$PORT/openapi/v3/api-docs

## Build and run the application from terminal

### Prerequisites
* Java 17+
* Apache Maven 3.8+
* Docker

### Install dependencies
```shell
mvn clean install
```

### Run the application

* Step 1 - build the image
```shell
 docker build -t marketing-customer .
```
* Step 2 - start the container
```shell
 docker run --name marketing-customer --rm -p8080:8080 -e "SPRING_PROFILES_ACTIVE=default" marketing-customer
```
* Step 3 - access the service
  <br><br>
  [**Open in browser.**] http://localhost:8080/openapi/swagger-ui.html
  <br>
  [**Monitor endpoints**] http://localhost:8080/actuator
  <br><br>
* Step 4 - stop the service and container
```shell
 docker stop marketing-customer
```
* Step 5 - delete the image
```shell
 docker image rm marketing-customer
```

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.7/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.7/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.1.7/reference/htmlsingle/index.html#actuator)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.1.7/reference/htmlsingle/index.html#web.reactive)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Articles and tutorials

* https://www.baeldung.com/java-validation
* https://reflectoring.io/bean-validation-with-spring-boot/
* https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=8.0#validator-customconstraints-simple
* https://swagger.io/resources/articles/adopting-an-api-first-approach/
* https://www.udemy.com/course/rest-api-design-the-complete-guide/