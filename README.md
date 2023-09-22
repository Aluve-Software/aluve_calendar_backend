# AluveCal Project README

This README provides an overview of the ** AluveCal** project, including its dependencies and configuration details.

## Project Overview

**AluveCal** is a Spring Boot project intended to serve as a starting point for developing web applications. It includes several common dependencies and configurations to kickstart your development process.

## Project Structure

The project follows a standard Maven directory structure and contains the following important files and directories:

- `src/main/java`: This directory contains the main Java source code for your application.
- `src/main/resources`: Configuration files and resources are stored here.
- `src/test`: Test classes for your application.
- `pom.xml`: The project's Maven POM (Project Object Model) file, specifying project dependencies and build settings.
- `application.properties`: Configuration file for various application properties.
- `README.md`: The file you are currently reading, providing information about the project.

## Dependencies

The project uses several dependencies to facilitate the development process. Here are the main dependencies listed in the `pom.xml` file:

### Spring Boot Dependencies

- **spring-boot-starter-data-jpa**: Provides support for Spring Data JPA and database access.
- **spring-boot-starter-thymeleaf**: Enables Thymeleaf templating for building web pages.
- **spring-boot-starter-security**: Adds Spring Security for authentication and authorization.
- **spring-boot-starter-web**: Provides basic web application support.
- **spring-boot-starter-validation**: Adds validation support.
- **spring-boot-starter-mail**: Allows sending emails from the application.

### Database

- **postgresql**: The PostgreSQL database driver for connecting to a PostgreSQL database.

### Testing

- **spring-boot-starter-test**: Provides testing utilities for Spring Boot applications.
- **spring-security-test**: Adds testing support for Spring Security.
- **hibernate-validator**: The Hibernate Validator library for validation.
- **validation-api**: The Java Validation API.

### Other

- **lombok**: A library that simplifies Java code by generating boilerplate code.

## Configuration

The project's configuration is defined in the `application.properties` file. Below are some of the key configurations:

### Database Configuration

- `spring.datasource.url`: The URL for the PostgreSQL database.
- `spring.datasource.username`: The database username (should be defined in your environment).
- `spring.datasource.password`: The database password (should be defined in your environment).
- `spring.jpa.hibernate.ddl-auto`: The Hibernate DDL auto strategy (set to `update` for development).
- `spring.jpa.show-sql`: Enable SQL query logging.
- `spring.jpa.properties.hibernate.format_sql`: Format SQL queries for better readability.
- `spring.jpa.properties.hibernate.dialect`: The PostgreSQL dialect for Hibernate.

### Email Configuration

- `spring.mail.host`: smtp.gmail.com.
- `spring.mail.port`: 587.
- `spring.mail.username`: Your email address (should be defined in your environment).
- `spring.mail.password`: Your email password (should be defined in your environment).
- `spring.mail.properties.mail.smtp.auth`:true.
- `spring.mail.properties.mail.smtp.starttls.enable`: true.

## Getting Started

1. Clone this repository to your local machine.

2. Configure the database and email properties in the `application.properties` file based on your environment.

3. Build and run the application using your preferred IDE or by executing `mvn spring-boot:run` in the project directory.

4. Access the application in your web browser using the appropriate URL (usually `http://localhost:8080`).

## Testing

The project includes unit testing support with JUnit. You can add your test cases in the `src/test` directory and run them using your IDE or by executing `mvn test`.

## Feedback and Contributions

Feel free to provide feedback, report issues, or contribute to this project by opening GitHub issues or pull requests. We appreciate your contributions to make this template project better.

Happy coding!
