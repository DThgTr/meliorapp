# Melior - Customer Transaction Management Sample REST Application
This is a backend Spring application, thus it only provides a REST API. **There is no UI**

## Melior Entity-relationship Model

![melior-ermodel](Melior_ER_model.png)

## Run Melior Application locally
This project use Apache Maven and come with a Maven wrapper.
### With Maven wrapper in command line
```
git clone https://github.com/DThgTr/meliorapp.git
cd /meliorapp
./mvnw spring-boot:run
```
Alternatively, the project can be manually downloaded from its Github repository at https://github.com/DThgTr/meliorapp.
### OpenAPI REST Documentation
These are meant to be accessed after application start:
* The REST endpoints are documented with swagger UI. They are accessible at: 
[http://localhost:9966/melior/swagger-ui.html](http://localhost:9966/melior/swagger-ui.html).
* The Open API description is accessible at: [http://localhost:9966/melior/v3/api-docs](http://localhost:9966/melior/v3/api-docs).

## Build Melior Application
### Generated Content
There are required classes generated during build process:
* DTO Mappers
* DTOs
* API template interfaces.

All of these classes are generated in the `target/generated-sources` folder.

Generated packages and their corresponding tools:

| Package name                | Tool             |
|-----------------------------|------------------|
| com.sample.meliorapp.Mapper | [MapStruct](https://mapstruct.org/)        |
| com.sample.meliorapp.rest   | [OpenAPI Generator maven plugin](https://github.com/OpenAPITools/openapi-generator/) |
### Generate code with Maven
Use the following command to generate them using the Maven wrapper:
* Build and Test:
```
./mvnw clean install
```
* Build only:
```
./mvnw clean install -DskipTests=true
```
* Test only:
```
./mvnw clean test
```
If you have Maven installed on your machine, you can use use its associated command ```mvn``` instead of the wrapper ```./mvnw```
