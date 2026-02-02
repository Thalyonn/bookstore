# Bookstore Application

This is a Spring Boot application for managing a bookstore with user accounts, shopping carts, and book catalog functionality.

## Database Configuration

1. Create a MySQL database named **`bookstore_db`**.
2. Update your `src/main/resources/application.properties` file with your database credentials:

```properties
spring.application.name=bookstore

spring.datasource.url=jdbc:mysql://localhost:3306/bookstore_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

3. Run the SpringApplication and Open with http://localhost:8080/
