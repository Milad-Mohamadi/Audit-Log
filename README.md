
# Spring Boot Accounting Project

> **A sample Spring Boot application for managing incomes, expenses, and other financial data with automatic auditing.**
>
> - **REST APIs** for CRUD operations on financial entities.
> - **MongoDB** for data storage and auditing logs.
> - **Spring AOP** (Aspect-Oriented Programming) for automatic method-level audit logging via `@Auditable`.
> - **HTTP Request/Response logging** filter to store raw request/response bodies in audit logs.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Tech Stack](#tech-stack)
5. [Prerequisites](#prerequisites)
6. [Getting Started](#getting-started)
7. [Configuration](#configuration)
8. [Endpoints](#endpoints)
9. [Auditing](#auditing)
10. [Running the Application](#running-the-application)
11. [Testing & Verification](#testing--verification)
12. [Future Improvements](#future-improvements)
13. [License](#license)

---

## 1. Overview

This project provides an **accounting platform** that allows you to:

- Manage **incomes** (درآمد), **expenses** (هزینه), **loans** (وام), **investments** (سرمایه), **withdrawals** (برداشت), **bank accounts** (حساب‌های بانکی), and **persons** (اشخاص).
- Track financial data with **CRUD operations** through RESTful APIs.
- Automatically log operations (creation, update, deletion) in a separate **audit log** collection in MongoDB.
- Optionally capture **raw HTTP request and response bodies** to store them for compliance, debugging, or tracing purposes.
- Interact with the application via the **command line**.

---

## 2. Features

- **Spring Boot**: Easy setup and deployment.
- **Spring Data MongoDB**: Simple persistence for domain entities and audit logs.
- **Spring AOP**:
  - `@Auditable` annotation to capture domain-level events (e.g., “CREATE_INCOME”).
  - Aspect intercepts annotated methods and stores relevant data into the audit log.
- **HTTP Request/Response Filter**: Logs the entire request and response payloads in the audit log for each request.
- **Spring Actuator**: Enables health checks and other monitoring endpoints.
- **Command-Line Interface (CLI)**: Interact with the application via Spring Shell or Picocli.

---

## 3. Architecture

The application follows a layered architecture with clear separation of concerns:

- **REST Controllers** handle HTTP requests and delegate to the service layer.
- **Service Layer** contains business logic and interacts with repositories.
- **Repositories** interact with MongoDB collections for data persistence.
- **Audit Components** (Aspect and Filters) handle automatic logging of operations and HTTP traffic.

---

## 4. Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data MongoDB**
- **Spring AOP**
- **MongoDB**
- **Spring Actuator** for monitoring
- **Spring Shell** or **Picocli** for CLI

---

## 5. Prerequisites

1. **Java 17+** installed.
2. **Maven** (or Gradle) installed.
3. **MongoDB** running locally on default port (`mongodb://localhost:27017`) or accessible via a connection string.

---

## 6. Getting Started

1. **Clone** this repository:
   ```bash
   git clone https://github.com/your-username/finance-audit.git
   cd finance-audit
   ```

2. **Build** the project:
   ```bash
   mvn clean install
   ```
   or
   ```bash
   ./gradlew build
   ```

3. **Run** the application:
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   ./gradlew bootRun
   ```

4. The server will start on port **8080** by default (configurable in `application.properties`).

---

## 7. Configuration

In your `src/main/resources/application.properties` (or `application.yml`):

```properties
# MongoDB connection
spring.data.mongodb.uri=mongodb://localhost:27017/finance_app

# Server port
server.port=8080

# Expose actuator endpoints
management.endpoints.web.exposure.include=*

# Logging level (optional, debug for aspect or filter)
logging.level.org.springframework.aop=DEBUG
logging.level.org.springframework.web.filter=DEBUG
```

Adjust properties for your environment (port, DB name, etc.).

---

## 8. Endpoints

Common REST endpoints for `Income` (as an example). Each endpoint is annotated with `@Auditable`:

- **GET** `/api/incomes`
  - Lists all income records.
- **GET** `/api/incomes/{id}`
  - Retrieves a single income record by ID.
- **POST** `/api/incomes`
  - Creates a new income record.
- **PUT** `/api/incomes/{id}`
  - Updates an existing income record.
- **DELETE** `/api/incomes/{id}`
  - Deletes an existing income record.

Similarly, you can create controllers for **Expense**, **Loan**, **Investment**, **Withdrawal**, **BankAccount**, and **Person**.

---

## 9. Auditing

### 9.1. `@Auditable` Annotation

Any service or controller method can be annotated with:

```java
@Auditable(action = "CREATE_INCOME", entityType = "Income", detail = "Creating new income record")
public Income createIncome(Income income) {
    // ...
}
```

When the method is called, the **AuditAspect** intercepts it and logs:

- **Action** (e.g., `CREATE_INCOME`)
- **EntityType** (e.g., `Income`)
- **Timestamp**
- **Optional** entityId or user info (if configured).

### 9.2. Request/Response Filter

The **RequestResponseLoggingFilter** captures full **HTTP request and response** bodies and stores them in the `audit_log` collection. This helps with:

- Troubleshooting
- Compliance (knowing exactly what payload was sent/received)
- Observability

### 9.3. Audit Log Schema

Stored in `audit_log` collection. Example fields:

- **timestamp**: `Instant` of the request or method call
- **method**: HTTP method (e.g., `POST`, `GET`)
- **uri**: The request path
- **httpStatus**: The response status code
- **requestBody**: Raw JSON from the HTTP request
- **responseBody**: The JSON returned to the client
- **durationMs**: Time taken to process the request
- **action**, **entityType**: If using `@Auditable` for domain events

---

## 10. Running the Application

Once started, you can interact with the application via tools like **cURL** or **Postman**:

```bash
# Create a new income
curl -X POST -H "Content-Type: application/json"      -d '{"amount":1000,"source":"Salary","date":"2024-12-25"}'      http://localhost:8080/api/incomes

# Get list of incomes
curl http://localhost:8080/api/incomes
```

Then check **MongoDB** to see:

- The created **Income** document in the `income` collection.
- The **audit records** in the `audit_log` collection.

---

## 11. Testing & Verification

1. **Unit Tests**:
   - Ensure each controller/service method performs as expected.
   - Check that `@Auditable` triggers a log entry by mocking the `AuditService`.

2. **Integration Tests**:
   - Make real HTTP calls and verify the `audit_log` collection has the correct entries.
   - Check request/response content is captured if using the logging filter.

3. **Manual Testing**:
   - Use **Postman** or **curl** to simulate typical user workflows.
   - Inspect MongoDB documents to ensure correctness.

---

## 12. Future Improvements

- **Security**: Integrate Spring Security or JWT to capture the authenticated user in the audit logs.
- **Async Logging**: Offload audit writes to a queue (Kafka/RabbitMQ) for higher throughput.
- **Filtering**: Exclude or mask sensitive data in the request/response (e.g., passwords).
- **Pagination & Reporting**: Build UI or reports to analyze financial data and audit logs.
- **Enhance AuditLog Schema**: Include more detailed information, such as client IP, user agent, etc.

---

## 13. License

[MIT License](LICENSE) – You’re free to use, modify, and distribute this project.

---

**Thank you for checking out this Spring Boot Accounting project!**  
If you have questions or suggestions, please open an issue or submit a pull request. Contributions are welcome.

Enjoy building your own **audit-driven** financial application!
