# Griha Khata - Rental Management API

## Project Overview
Griha Khata is a Spring Boot API for rental property management, including authentication, buildings/units, and rent ledger workflows.

## Prerequisites
- Java 21
- Maven 3.9+
- MySQL 8.x

## Database Setup
1. Create the database.
2. Create a MySQL user (or use an existing one) and grant access.

Example SQL:
```
CREATE DATABASE griha_khata;
CREATE USER 'springstudent'@'localhost' IDENTIFIED BY 'springstudent';
GRANT ALL PRIVILEGES ON griha_khata.* TO 'springstudent'@'localhost';
FLUSH PRIVILEGES;
```

## Required Configuration
Set these values in `src/main/resources/application.properties` or via environment variables:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `security.jwt.secret`
- `security.jwt.expiration-ms`
- `security.jwt.refresh-expiration-ms`

Example (application.properties):
```
spring.datasource.url=jdbc:mysql://localhost:3306/griha_khata?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=springstudent
spring.datasource.password=springstudent
security.jwt.secret=MySuperSecretJwtKeyForHmacSha256_ChangeMe_2026
security.jwt.expiration-ms=86400000
security.jwt.refresh-expiration-ms=2592000000
```

## Run the Application
1. Install dependencies and run the app:
```
./mvnw spring-boot:run
```
2. Flyway migrations run automatically at startup.

## Swagger UI
Once the app is running, open:
```
http://localhost:8080/swagger-ui/index.html
```

