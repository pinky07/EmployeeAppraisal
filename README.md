
# Employee

EmployeeAppraisal microservice documentation.

## Spring profiles

This project includes 5 different profiles that can be set in the property `spring.profiles.active` in the file 
`src/main/resources/application.yml`.

- `default` Default Spring profile. Remember to include it when you any extra profiles in the configuration.
- `dev` Development server profile. Includes a PostgreSQL driver.
- `local-h2` Development profile. Includes an in memory database, self signed certificates and other useful configurations 
when developing.
- `local-postgresql` Development profile. Includes an driver for PostgreSQL testing. A local PostgreSQL installation is required.
- `oauth2` Enables the application's OAuth2 security. Disable this proflie when you want to test the endpoints without 
providing an access token.

You can enable and disable profiles at will. But make sure that all are enabled before pushing changes to master.

## Swagger codegen

This project includes code that was generated by the [swagger-codegen](https://github.com/swagger-api/swagger-codegen) project.
By using the [OpenAPI-Spec](https://github.com/swagger-api/swagger-core), you can easily generate an API stub.
This is an example of building API stub interfaces in Java using the Spring framework.

The stubs generated can be used in your existing Spring-MVC or Spring-Boot application to create controller endpoints
by adding ```@Controller``` classes that implement the interface. Eg:
```java
@Controller
public class PetController implements PetApi {
// implement all PetApi methods
}
```

You can also use the interface to create [Spring-Cloud Feign clients](http://projects.spring.io/spring-cloud/spring-cloud.html#spring-cloud-feign-inheritance).Eg:
```java
@FeignClient(name="pet", url="http://petstore.swagger.io/v2")
public interface PetClient extends PetApi {

}
```
