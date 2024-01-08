# marketing-customer
Customer data (backend part) service that contains minimal operations

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

## Tech stack
- Java 17 or above
- Spring Boot 3.x
- Build system: maven or gradle
- Docker container
- CI/CD pipeline
- Well design production ready application
- Persistence in an SQL DB (in memory DB)
- Maximum 10k customers.