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
* Step 3 - access the service <br>
  <br>
  [**Open in browser.**] (http://localhost:8080/openapi/swagger-ui.html)
  <br><br>
* Step 4 - stop the service and container
```shell
 docker stop marketing-customer
```
* Step 5 - delete the image
```shell
 docker image rm marketing-customer
```