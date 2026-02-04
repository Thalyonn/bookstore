# Bookstore Application

This is a Spring Boot application for managing a bookstore with user accounts, shopping carts, and book catalog functionality.


---

## Features
- **User**
  - Browse available books
  - Search and filter by title and category
  - Add books to cart
  - View past orders
  - checkout orders in cart
- **Admin**
  - Add new books
  - Add new categories
  - Register new admins

---

## Tech Stack
- **Backend**: Spring Boot, Spring Data JPA, Hibernate
- **Database**: MySQL
- **Frontend**: HTML, CSS, JavaScript (vanilla)
- **Security**: Spring Security - role-based access (`USER`, `ADMIN`)

---


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

```
3. Run the SpringApplication and Open with http://localhost:8080/
