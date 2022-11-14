# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#web)
* [Spring Session](https://docs.spring.io/spring-session/reference/)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#data.sql.jdbc)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#actuator)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Guides - Nuxt & Vue (By R. Demers)
The following guides illustrate how to use Vue, Nuxt and Vuetiny concretely:
* [Create the initial SpringBoot Application - Be sure to add Spring WEB dependency](https://start.spring.io/)
* npm i nuxi -- Nuxt tooling optionnelle

npx nuxi init frontend
cd frontend
npm install
npm run dev -- -o
npm run build
nom run generate
npm run preview

npx nuxi upgrade

http://localhost:8080/api/messages/hello

adobe color

vuetify
npm add vuetify@next sass

# Spring Boot H2 Database CRUD example with security

## Run Spring Boot application
```
mvn spring-boot:run
```
```
GET http://localhost:8080/jwt/ping
```
```
POST http://localhost:8080/jwt/token
```

```
jeton valide : eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZWIwODYzZi01NGMwLTRkY2MtYjQxOS02ZDUzNzFmYjcyZmEiLCJzdWIiOiJqd3Rfcm9vdCIsImxxLnJvbGVzIjoiUk9MRV9TRUxFQ1QsUk9MRV9JTlNFUlQsUk9MRV9VUERBVEUsUk9MRV9ERUxFVEUiLCJpYXQiOjE2MzE2MzI4NDV9.toiK_g2cJDBhxidE3lKtHGMPQ7OnjUAz3VSIJkIGrfE
```
```
http://localhost:8080/swagger-ui.html


The Swagger UI page will then be available at http://server:port/context-path/swagger-ui.html and the OpenAPI description will be available at the following url for json format: http://server:port/context-path/v3/api-docs
```
