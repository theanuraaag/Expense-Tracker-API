# API Reference — Expense Tracker API

This document describes the public HTTP API exposed by the application. Schemas are derived from DTOs in the codebase.

Base URL: http://localhost:8080

Authentication: JWT bearer token returned by `/api/auth/login` and `/api/auth/register`. Include header:

Authorization: Bearer <token>

----

## POST /api/auth/register
Register a new user.

Request
- Content-Type: application/json
- Body (RegisterRequest):

```json
{
  "email": "alice@example.com",
  "password": "password123"
}
```

Responses
- 201 Created
  - Body (AuthResponse):
```json
{
  "accessToken": "<jwt>"
}
```

Source: `src/main/java/expense_tracker_api/auth/AuthController.java`

----

## POST /api/auth/login
Authenticate a user and receive a JWT token.

Request
- Content-Type: application/json
- Body (LoginRequest):

```json
{
  "email": "alice@example.com",
  "password": "password123"
}
```

Responses
- 200 OK
  - Body (AuthResponse):

```json
{
  "accessToken": "<jwt>"
}
```

Source: `src/main/java/expense_tracker_api/auth/AuthController.java`

----

## GET /api/health
Simple health check.

Response
- 200 OK
```json
{
  "status": "UP"
}
```

Source: `src/main/java/expense_tracker_api/web/HealthController.java`

----

## Expense endpoints
Base path: `/api/expenses`

Models

- ExpenseRequest (used for create/update)

```json
{
  "amount": 23.5,
  "category": "GROCERIES",
  "description": "optional text, max 500 chars",
  "expenseDate": "YYYY-MM-DD"
}
```

- ExpenseResponse

```json
{
  "id": 1,
  "amount": 23.5,
  "category": "GROCERIES",
  "description": "Weekly shopping",
  "expenseDate": "2026-05-01"
}
```

Source: `src/main/java/expense_tracker_api/expense/dto/ExpenseRequest.java`, `src/main/java/expense_tracker_api/expense/dto/ExpenseResponse.java`

----

### GET /api/expenses
List expenses.

Query parameters
- `filter` (optional): string filter applied by service (implementation-specific)
- `startDate` (optional): ISO date (YYYY-MM-DD)
- `endDate` (optional): ISO date (YYYY-MM-DD)

Response
- 200 OK — Array of ExpenseResponse

Example:

```bash
curl "http://localhost:8080/api/expenses?startDate=2026-05-01&endDate=2026-05-31" \
  -H "Authorization: Bearer <token>"
```

----

### GET /api/expenses/{id}
Retrieve an expense by id.

Path parameters
- `id` (number)

Response
- 200 OK — ExpenseResponse
- 404 Not Found — if expense missing

----

### POST /api/expenses
Create a new expense.

Request
- 201 Created
- Body: ExpenseRequest (see model above)

Response
- 201 Created — ExpenseResponse

Example:

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"amount":45.00,"category":"LEISURE","description":"Cinema","expenseDate":"2026-05-05"}'
```

----

### PUT /api/expenses/{id}
Update an existing expense.

Request
- Path param: `id`
- Body: ExpenseRequest

Response
- 200 OK — ExpenseResponse
+- 404 Not Found — if expense missing

----

### DELETE /api/expenses/{id}
Delete an expense.

Response
- 204 No Content

----

## ExpenseCategory values
Valid categories (enum `ExpenseCategory`):
- GROCERIES
- LEISURE
- ELECTRONICS
- UTILITIES
- CLOTHING
- HEALTH
- OTHERS

Source: `src/main/java/expense_tracker_api/domain/expense/ExpenseCategory.java`

----

## Notes & next steps
- The project includes Springdoc/OpenAPI configuration and exposes Swagger UI at `/swagger-ui.html`. For a machine-readable API spec use `/v3/api-docs`.
- Consider adding an official `API.md` to the repository (this file) and updating it with examples from integration tests for more accurate payloads and error cases.

