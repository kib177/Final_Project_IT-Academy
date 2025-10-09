<img src="https://socialify.git.ci/kib177/Final_Project_IT-Academy/image?language=1&name=1&owner=1&pattern=Signal&pulls=1&stargazers=1&theme=Light" alt="Final_Project_IT-Academy" width="640" height="320" />

## Project Description

Personal Finance Tools is a web application for personal financial management, built in Java using Spring and PostgreSQL. The application allows users to manage their accounts, track income and expenses, and analyze financial statistics.

---

## Core Features

- **Account Management**: Create, delete, and modify user accounts.

- **Financial Accounts**: Create and manage bank accounts, cash, and deposits.

- **Transactions**: Record income and expenses for accounts.

- **Categories**: Classify transactions into categories for easy analysis.

- **Currencies**: Support for multiple currencies.

- **Audit Log**: Track all user activities within the system.

- **User Roles**: Role-based access control for users, managers, and administrators.

---

## Technology Stack

- Java 21
- Spring Boot
- Spring Security (JWT authentication)
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven
- Hibernate

---

## Technology Stack

1. Ensure `Java 21` and `PostgreSQL` are installed.
2. Create a database in `PostgreSQL`.
3. Configure connection settings in `application.yml`
4. Build the project: `./mvn clean install`
5. Run the application: `./mvn spring-boot:run`

---

## Services / Modules

| Service              | Port | Description                                                                       |
|----------------------|------|-----------------------------------------------------------------------------------|
| `postgreSQL`         | 5432 | Database service for storing app data.                                            |
| `user-service`       | 8080 | Handles user registration, authentication,verification, and user data management. |
| `classifier-service` | 8083 | Provides classification and reference data.                                       |
| `account-service`    | 8082 | Manages user accounts and financial operations.                                   |
| `audit-service`      | 8081 | Tracks changes and events in the system.                                          |
| `scheduler-service`  | 8084 | Handles scheduled operations.                                                     |
| `report-service`     | 8085 | Generates and provides financial reports.                                         |

---

## Specification
This project was made in compliance with openAPI specifications of [ITAcademyPersonalFinanceTools](https://github.com/WestDragon/ITAcademyPersonalFinanceTools)
