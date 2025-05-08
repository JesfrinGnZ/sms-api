# Full Stack Developer Test
A technical test project built with Spring Boot that allows users to "send" SMS.

---

## **Instructions to Run the Project**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/JesfrinGnZ/sms-api.git
   cd sms-api
2. **Ensure Java is set to the 21 version.**
5. **Build the project using Maven.**
      ```bash 
    mvn clean install
6. **Run the application.**
     ```bash
   mvn spring-boot:run

7. **The application will start on:**
   http://localhost:8080

## Authentication Flow

### 1. Register a New User

**Endpoint**: `POST /signup`  
**URL**: `http://localhost:8080/signup`

**Payload**:

```json
{
  "email": "user@example.com",
  "password": "password"
}
```
**Response**:
```json
{
   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
   "expiresIn": 3600000
}

```

### 2. Login as an Existing User

**Endpoint**: `POST /login`  
**URL**: `http://localhost:8080/login`

**Payload**:

```json
{
  "email": "user@example.com",
  "password": "yourSecurePassword"
}
```
**Response**:
```json
{
   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
   "expiresIn": 3600000
}

```
Use the returned token in the Authorization header for protected endpoints(/sms).

### 3. Send SMS Message

**Endpoint**: `POST /sms`  
**URL**: `http://localhost:8080/sms`  
**Authorization**: Requires Bearer Token in the header

**Payload**:

```json
{
  "message": "Dear user, we inform you that your invoice has been generated. Please check your account for details. Thank you for using our service."
}
```

**Response**:

```json
{
   "parts": [
      "Football is a family of team sports that involve, to varying degrees, kicking a ball to score a goal. Unqualified, the word football generally ... - Part 1 of 3",
      "means the form of football that is the most popular where the word is used. Sports commonly called football include association football ... - Part 2 of 3",
      "(known as soccer in Australia, Canada, South Africa, the United States, ... - Part 3 of 3"
   ]
}
```

---

## Frontend Usage

The frontend interface is intuitive and minimal. Users can:

1. Register with an email and password.
2. Automatically receive a JWT token upon successful registration or login.
3. Use the provided interface to send an SMS by entering the message content.

All API calls are handled transparently in the background, and no manual token management is required by the user. Once logged in, users can immediately interact with the API through the UI.

## Dependencies

The project uses the following dependencies:

- **Spring Boot Starter Web**  
  For building RESTful APIs using Spring MVC.

- **Spring Boot Starter Data JPA**  
  For interacting with the H2 in-memory database using Spring Data JPA.

- **Spring Boot Starter Security**  
  For handling authentication and securing API endpoints.

- **Spring Boot Starter Validation**  
  For validating incoming request payloads using annotations like `@Email`, `@NotBlank`, etc.

- **Spring Boot Starter Test**  
  Provides testing support for unit and integration tests.

- **Lombok**  
  Reduces boilerplate code through annotations like `@Getter`, `@Setter`, `@Builder`.

- **H2 Database**  
  In-memory database used for development and testing.

- **JJWT (Java JWT)**  
  Used to generate and parse JSON Web Tokens for user authentication:
   - `jjwt-api`
   - `jjwt-impl`
   - `jjwt-jackson`

## Architecture & Design Decisions

The backend is structured following **layered architecture** and separation of concerns principles. Key design decisions include:

- **Controller Layer (`controller`)**  
  Handles HTTP requests and delegates processing to services. `AuthController` manages authentication logic, while `MessageController` handles SMS operations.

- **DTO Layer (`dto`)**  
  Ensures that only relevant and validated data crosses the API boundary. It improves clarity and prevents entity exposure.

- **Entity Layer (`entity`)**  
  Represents the data model persisted to the H2 in-memory database.

- **Service Layer (`service`)**  
  Encapsulates business logic for authentication (`AuthService`), JWT handling (`JwtService`), and SMS splitting (`SmsSplitterServiceImpl`).

- **Repository Layer (`repository`)**  
  Interacts with the database using Spring Data JPA. For now, `UserRepository` is the main access point for persistence.

- **Security Layer (`security`)**  
  Implements authentication using Spring Security and JWT. It includes:
   - `WebSecurityConfig` for HTTP security configuration.
   - `JwtAuthenticationFilter` for token extraction and validation.
   - `ApplicationConfiguration` for bean definitions like `AuthenticationManager`.

- **Exception Handling (`exception`)**  
  Uses a centralized `GlobalExceptionHandler` to manage errors consistently with custom exceptions like `UserException` and `DomainException`.

This modular design improves **testability**, **readability**, and **scalability**, making it easier to extend and maintain.

---

## Improvements with More Time

If more time were available, the following improvements would be prioritized:

- **Add a Refresh Token mechanism** for longer session persistence and improved security.
- **Extend the `User` model** with additional fields like `fullName`, `createdAt`, or `roles`.
- **Improve password validation**, enforcing strong password policies (e.g., length, digits, symbols).
- **Add Swagger/OpenAPI integration** for API documentation and easier frontend/backend collaboration.
- **Include integration tests** using `@SpringBootTest` and `Testcontainers` to validate end-to-end flows.
- **Introduce a `BaseException` hierarchy** for more consistent error structures.
- **Implement logging and metrics collection** for observability (e.g., using Micrometer + Prometheus).
- **Enable environment-based configurations** with application profiles for development and production environments.
- **Apply role-based access control (RBAC)** for protected endpoints beyond simple authentication.

These improvements would help move the project toward **production readiness** and allow for **scalability and maintainability** in real-world environments.

## Design & Analytical Questions

### 1. Architecture Decisions
The code is structured based on a layered architecture to promote separation of concerns and modularity. The main considerations included:
- Keeping the responsibilities clearly separated (controller, service, repository).
- Ensuring testability and maintainability by isolating business logic from I/O layers.
- Organizing security-related logic in a dedicated `security` package for clarity and reuse.
- Using DTOs to decouple the domain model from the exposed API.
- Applying centralized exception handling to provide consistent error responses.

### 2. API Design
The API was designed using REST principles:
- Endpoints follow resource-based naming (e.g., `/auth/signup`, `/sms/send`).
- Clear separation between authentication and SMS-related operations.
- Used meaningful HTTP status codes for success and failure.
- While versioning (e.g., `/api/v1/`) was not implemented due to project scope, the structure can be easily adapted to include it.
- Error responses are standardized using a `@RestControllerAdvice`-based global exception handler.
  It catches known exceptions such as `IllegalArgumentException`, `BadCredentialsException`, and custom `DomainException`, and returns structured JSON responses.
  Most responses include a consistent `message` field, and the appropriate HTTP status code depending on the error context (e.g., 400, 404, 500).

### 3. State Management (If implemented)
On the frontend (React), state is managed locally using `react-hook-form` for form inputs.  
Authentication tokens are persisted using `localStorage`, which ensures they survive page reloads.  
This approach is simple and effective for small to medium-sized applications. If the app grows in complexity, tools like Redux could be introduced for global state management and token handling.

### 4. Security (If implemented)
Authentication is implemented using Spring Security and JWT (JSON Web Tokens):
- On successful login or signup, a JWT is issued and returned to the frontend.
- The frontend stores the token in `localStorage` and sends it in the `Authorization` header for subsequent requests.
- The backend validates the token using a custom `JwtAuthenticationFilter`, ensuring stateless and secure session management.

### 5. Scalability & Maintainability
If the application needed to scale to support more users and teams:
- Migrate from H2 to a production-ready database like PostgreSQL or Oracle.
- Introduce role-based access control (RBAC) to support user authorization levels.
- Add caching (e.g., Caffeine, Redis) to reduce database load.
- Implement pagination and filtering for endpoints returning large datasets.
- Add CI/CD pipeline for automated testing, deployment, and monitoring.

### 6. Time Constraints
Due to time constraints, the following features were intentionally left out:
- Refresh token mechanism for session renewal.
- Advanced validation rules for secure passwords (e.g., regex patterns).
- API documentation using Swagger/OpenAPI.
- Integration and end-to-end testing with `Testcontainers`.
- Profile-based configuration for multiple environments.
- Role management and user metadata fields.

These were noted as future improvements if additional time were available.

### 7. Time Management

The project was developed over the course of approximately **15 hours**. 
The focus during this time was to deliver a clean, functional, and testable solution that satisfied the core requirements of the assessment.