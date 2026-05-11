# Expense Tracker API

Lightweight REST API for tracking expenses. Built with Spring Boot, H2 (in-memory) for development, and JWT-based authentication.

## Features
- User registration and login (JWT tokens)
- CRUD for expenses with categories and date filtering
- OpenAPI (Swagger) documentation available
- In-memory H2 database for easy local development

## Quick links
- H2 console: /h2-console
- Swagger UI: /swagger-ui.html
- OpenAPI JSON: /v3/api-docs

## Prerequisites
- Java 17+ (or the project's configured JDK)
- Maven (the project includes the Maven wrapper `mvnw`)

## Run locally
1. Start the application:

```bash
./mvnw spring-boot:run
```

2. App runs by default on http://localhost:8080
3. H2 console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:expensedb`)
4. Swagger UI: http://localhost:8080/swagger-ui.html

## Configuration
Configuration is driven by `src/main/resources/application.yml`. Important values:

- `spring.datasource.url`: default uses H2 in-memory DB
- `app.jwt.secret`: default development secret is present in `application.yml`. Set the `JWT_SECRET` environment variable in production.
- `app.jwt.expiration-ms`: token expiration (ms)

Example environment variables:

```bash
export JWT_SECRET="your-strong-256bit-secret"
```

## API quick start (curl)

Register a user:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"password123"}'
```

Login:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"password123"}'
```

Response returns `{"accessToken":"...jwt..."}`. Use this token in `Authorization: Bearer <token>` header for protected endpoints.

Create an expense (example):

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "amount": 23.50,
    "category": "GROCERIES",
    "description": "Weekly shopping",
    "expenseDate": "2026-05-01"
  }'
```

List expenses:

```bash
curl http://localhost:8080/api/expenses -H "Authorization: Bearer <token>"
```

Get health:

```bash
curl http://localhost:8080/api/health
```

## Project structure (relevant files)
- Controllers:
  - `src/main/java/expense_tracker_api/auth/AuthController.java` (auth endpoints)
  - `src/main/java/expense_tracker_api/expense/ExpenseController.java` (expense endpoints)
  - `src/main/java/expense_tracker_api/web/HealthController.java` (health endpoint)
- DTOs:
  - `src/main/java/expense_tracker_api/auth/dto` (RegisterRequest, LoginRequest, AuthResponse)
  - `src/main/java/expense_tracker_api/expense/dto` (ExpenseRequest, ExpenseResponse)
- Configuration:
  - `src/main/resources/application.yml`

## Contributing
Contributions are welcome. Please open an issue or a PR with a clear description and tests where applicable.

## License
This repository does not include a LICENSE file. Add a LICENSE to declare project licensing.

