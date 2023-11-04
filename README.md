# Melior - Customer Transaction Management Sample Application
This is a backend Spring application, thus it only provides a REST API. **There is no UI**

## Melior Entity-relationship Model

![melior-ermodel](meliorER.png)

## Run Melior Application locally
This project use Apache Maven and run using a Maven wrapper.
### With Maven command line
```
git clone https://github.com/DThgTr/meliorapp.git
cd /meliorapp
./mvnw spring-boot:run
```
REST endpoints are exposed using Swagger UI. It can be accessed at :[http://localhost:9966/melior/](http://localhost:9966/melior/swagger-ui.html)
