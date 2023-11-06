# Melior - Customer Transaction Management Sample Application
This is a backend Spring application, thus it only provides a REST API. **There is no UI**

## Melior Entity-relationship Model

![melior-ermodel](Melior_ER_model.png)

## Run Melior Application locally
This project use Apache Maven and come with a Maven wrapper.
### With Maven command line
```
git clone https://github.com/DThgTr/meliorapp.git
cd /meliorapp
./mvnw spring-boot:run
```
REST endpoints are documented using Swagger UI. It can be accessed at: [http://localhost:9966/melior/swagger-ui.html](http://localhost:9966/melior/swagger-ui.html)
