## Getting Started

Build a backend service that handles a (very simple) recruiting process.

### Prerequisites
```
* Java 8
* Maven 3.3.9 or higher
* Git 2.9.0 or higher
```

### Installing
```
git clone https://github.com/Sumitbhoyar/rest-api.git
cd java-interview-exercise
mvn clean install
```

## Running the tests
```
mvn clean test
```

## Deployment
```
mvn spring-boot:run
```

## API Testing
API can be tested using Swagger2 UI

URL: http://localhost:8080/swagger-ui.html

![Alt text](documents/images/SwaggerUI.png?raw=true)

API EndPoints

![Alt text](documents/images/APIEndpoints.png?raw=true)

Steps to test

![Alt text](documents/images/SwaggerSteps.png?raw=true)

## Features

* API for Offer and Application supports Pagination
* API design supports Richardson Maturity Model at level 3
* API response supports HATEOAS
* Code is covered by unit tests using JUnit and BDD using Cucumber
* API's are documented using swagger. It could be launched using following URL- http://localhost:8080/swagger-ui.html
* Application could be deployed using docker. However it could not be tested due to time constraint.

