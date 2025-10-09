<img src="https://socialify.git.ci/kib177/Final_Project_IT-Academy/image?language=1&name=1&owner=1&pattern=Signal&pulls=1&stargazers=1&theme=Light" alt="Final_Project_IT-Academy" width="640" height="320" />

## Описание проекта

Personal Finance Tools - это веб-приложение для учета личных финансов, разработанное на Java с использованием Spring и PostgreSQL. Приложение позволяет пользователям управлять своими счетами, отслеживать доходы и расходы, а также анализировать финансовую статистику.

---

## Основные функции

- **Управление аккаунтами**: Создание, удаление, изменение аккаунтов.
- **Управление счетами**: Создание и управление банковскими счетами, наличными и депозитами
- **Операции**: Добавление доходов и расходов по счетам
- **Категории**: Классификация операций по категориям для удобного анализа
- **Валюты**: Поддержка различных валют
- **Аудит**: Журнал всех действий пользователей в системе
- **Роли пользователей**: Разделение прав доступа между пользователями, менеджерами и администраторами
---

## Технологии

- Java 21
- Spring Boot
- Spring Security (JWT аутентификация)
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven
- Hibernate
---


## Установка и запуск

1. Убедитесь, что у вас установлены Java 21 и PostgreSQL
2. Создайте базу данных в PostgreSQL
3. Настройте параметры подключения в `application.yml`
4. Соберите проект: `./mvn clean install`
5. Запустите приложение: `./mvn spring-boot:run`

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
