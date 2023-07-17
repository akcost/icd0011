# Java REST API application

Authenticate users, create orders, delete orders, get all orders, find a specific order.

### Technologies used
Spring, Spring Framework, Spring MVC, Spring security, JPA, Hibernate, PostgreSQL, JWT, Validation
### Setup
1) Create a postgres database and add the url, user and pass to the ```application.properties``` file in src/main/resources.
2) [Optional] You can use `docker-compose.yml` to create a database using Docker and then do the 1. step.
3) Run app by running `gradlew appRun` for Windows cmd and `./gradlew appRun` for Mac/Linux/Windows PowerShell.
### Testing

Run tests from `app/src/test/java/tests/Hw10.java`.
