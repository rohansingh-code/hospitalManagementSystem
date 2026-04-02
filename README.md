# 🏥 Hospital Management System

A production-grade backend REST API for managing hospital operations — built with **Spring Boot 4** and secured with **JWT authentication** and **Role-Based Access Control (RBAC)**.

---

## ✨ Features

### 🔐 Authentication & Authorization
- **JWT-based stateless authentication** — Login returns a signed JWT token (valid for 10 minutes) used for all subsequent requests via `Authorization: Bearer <token>` header.
- **Role-Based Access Control (RBAC)** — Three roles (`ADMIN`, `DOCTOR`, `PATIENT`) with endpoint-level access restrictions enforced through Spring Security's filter chain.
- **Signup with automatic Patient profile creation** — New users are registered with roles and a linked `Patient` entity is automatically created.
- **BCrypt password hashing** — All passwords are securely encoded before storage.

### 🏗️ Core Domain
- **Patient Management** — Registration, profile retrieval, blood group tracking, insurance linking, and paginated listing (admin only).
- **Doctor Management** — Public doctor listing, specialization tracking, and department association.
- **Appointment System** — Create appointments between patients and doctors, view doctor-specific appointments, and reassign appointments to different doctors.
- **Insurance Management** — Assign or remove insurance policies from patients with full cascade support.
- **Department Management** — Departments with many-to-many doctor associations and a designated head doctor.

### 🛡️ Error Handling
- **Global exception handler** via `@RestControllerAdvice` covering:
  - `AuthenticationException` → 401 Unauthorized
  - `AccessDeniedException` → 403 Forbidden
  - `JwtException` → 401 Unauthorized
  - `UsernameNotFoundException` → 404 Not Found
  - Generic exceptions → 500 Internal Server Error
- Structured `ApiError` response with timestamp, error message, and HTTP status.

---

## 🏛️ Architecture

```
src/main/java/com/example/springboot/hospitalManagement/
├── Entity/                  # JPA Entities (User, Patient, Doctor, Appointment, etc.)
│   └── type/                # Enums (RoleType, BloodGroupType)
├── Repository/              # Spring Data JPA Repositories
├── service/                 # Service interfaces
│   └── impl/                # Service implementations
├── controller/              # REST Controllers
├── dto/                     # Request/Response DTOs
├── security/                # JWT filter, auth service, security config
├── config/                  # App configuration (ModelMapper, PasswordEncoder)
└── error/                   # Global exception handling
```

---

## 🔧 Tech Stack

| Layer         | Technology                          |
|---------------|-------------------------------------|
| Language      | Java 21                             |
| Framework     | Spring Boot 4.0.3                   |
| Security      | Spring Security + JWT (jjwt 0.12.6) |
| Database      | PostgreSQL                          |
| ORM           | Spring Data JPA / Hibernate         |
| DTO Mapping   | ModelMapper 3.2.4                   |
| Boilerplate   | Lombok                              |
| Build Tool    | Maven                               |

---

## 📡 API Endpoints

### Public (No authentication required)
| Method | Endpoint              | Description           |
|--------|-----------------------|-----------------------|
| GET    | `/public/doctors`     | List all doctors      |
| POST   | `/auth/signup`        | Register a new user   |
| POST   | `/auth/login`         | Login and receive JWT |

### Authenticated (Any logged-in user)
| Method | Endpoint                | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/patients/appointments`| Create a new appointment   |
| GET    | `/patients/profile`     | Get patient profile        |

### Doctor / Admin Only
| Method | Endpoint               | Description                          |
|--------|------------------------|--------------------------------------|
| GET    | `/doctors/appointments`| Get appointments for logged-in doctor|

### Admin Only
| Method | Endpoint           | Description                             |
|--------|--------------------|-----------------------------------------|
| GET    | `/admin/patients`  | List all patients (paginated)           |

> All endpoints are prefixed with `/api/v1` (configured via `server.servlet.context-path`).

---

## 📦 Entity Relationship Overview

```
User (1) ── (1) Patient ── (1) Insurance
  │                │
  │                └──── (M) Appointment (M) ──── (1) Doctor
  │                                                    │
  └──── (1) Doctor ──── (M) Department
```

- `User` implements `UserDetails` and holds a set of `RoleType` roles.
- `Patient` and `Doctor` share the `User` primary key via `@MapsId` + `@OneToOne`.
- `Patient` ↔ `Insurance` is a bidirectional `@OneToOne` with cascade and orphan removal.
- `Patient` ↔ `Appointment` is `@OneToMany` with cascade remove.
- `Doctor` ↔ `Department` is a `@ManyToMany` relationship.

---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL (running on `localhost:5432`)

### 1. Clone the Repository
```bash
git clone https://github.com/rohansingh-code/hospitalManagementSystem.git
cd hospitalManagementSystem
```

### 2. Create the PostgreSQL Database
```sql
CREATE DATABASE hospitalDb;
```

### 3. Set Environment Variables
```bash
export DB_USERNAME=your_postgres_username
export DB_PASSWORD=your_postgres_password
```

You also need to set the JWT secret key. Add it to your `application.properties` or pass as an environment variable:
```properties
jwt.secretKey=your-256-bit-secret-key-here
```

### 4. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api/v1`.

---

## 📝 Sample Requests

### Sign Up
```bash
curl -X POST http://localhost:8080/api/v1/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john@example.com",
    "password": "securePass123",
    "name": "John Doe",
    "roles": ["PATIENT"]
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john@example.com",
    "password": "securePass123"
  }'
```

### Access Protected Endpoint
```bash
curl http://localhost:8080/api/v1/patients/profile \
  -H "Authorization: Bearer <your-jwt-token>"
```

---

## ⚠️ Notes
- `spring.jpa.hibernate.ddl-auto` is set to `create` — tables are dropped and recreated on every startup. Change to `update` or `validate` for production.
- SQL data seeding via `data.sql` is present but currently **disabled**.
