## order-service

- The order-service is order service which is capable of placing and fetching orders of a user.
- This service makes of `RestTemplate` to communicate with `product-service` and `user-service`.
- This service is also includes example of request and response `header-validation` and global exception handling
  using `@RestControllerAdvice` and `ResponseEntityExceptionHandler`.
- Further it integrates `actuator` for health monitoring and `resilience4j` to implement `retry` fault tolerance
  strategy.

### RestTemplate

- RestTemplate provides a convenient way to interact with RESTful web services. It simplifies the process of making HTTP
  requests and handling HTTP responses in Java applications. With RestTemplate, you can send HTTP requests (GET, POST,
  PUT, DELETE, etc.) to remote RESTful APIs and receive responses.

### @RestControllerAdvice

- @RestControllerAdvice is a special type of @ControllerAdvice. It is used to handle exceptions globally and provide
  centralized exception handling. It is specifically designed for RESTful APIs where the response is typically in JSON
  format.
- When you annotate a class with @RestControllerAdvice, it serves as a global exception handler for all @RestController
  annotated classes in your application. It allows you to handle exceptions thrown by your RESTful endpoints and
  customize the error responses sent back to clients.

### ResponseEntityExceptionHandler

- ResponseEntityExceptionHandler is a base class that acts as a convenient base class for
  handling exceptions globally in the application, especially for RESTful APIs. It is an extension of the
  ResponseEntityExceptionHandler class, and it provides default implementations for several exception handling methods.

- When you extend ResponseEntityExceptionHandler and override its methods, you can customize the handling of specific
  exceptions and provide appropriate error responses to clients. This approach is particularly useful for handling
  exceptions globally across multiple controllers and endpoints in the application.

### Actuator

- Actuator is a set of production-ready features bundled with Spring Boot to help monitor and manage an
  application. It provides a wide range of built-in tools and endpoints that can be used for monitoring, health checks,
  metrics, application information, and more.
- Following dependency is required to integrate actuator:

```
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  ```

- Some key features of Spring Boot Actuator are **Health Checks**, **Metrics**, **Environment/Application Information**,
  **Logging**, and **Tracing Endpoints**

### Resilience4j

- Resilience4j is a lightweight, fault tolerance library that provides higher-order functions (
  decorators) to enhance any functional interface, lambda expression, or method reference with
  a `Circuit Breaker`, `Rate
  Limiter`, `Retry`, and `Bulkhead`.
- It is designed to be easy to use and understand while offering powerful
  features for building resilient and fault-tolerant systems.
- Following dependency is required to integrate Resilience4j:

```
  <dependency>
      <groupId>io.github.resilience4j</groupId>
      <artifactId>resilience4j-spring-boot2</artifactId>
      <version>2.1.0</version>    
  </dependency>
```
