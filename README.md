# Hospital Management System

A demo Hospital Management System built with Spring Boot, JPA/Hibernate and Spring Security.  
This project models a small hospital domain (Users, Doctors, Patients, Departments, Appointments, Insurance, Permissions) and includes REST controllers, service layer logic, repository interfaces, ModelMapper for DTO mapping and tests.

Repository package: `com.adityayadavlearning.springboot.hospitalManagement`

## Quick overview

- Spring Boot application (main: `HospitalManagementApplication`)
- Domain entities: `User`, `Doctor`, `Patient`, `Appointment`, `Department`, `Permission`, `Insurance`
- DTOs and response objects, mapped via ModelMapper
- Repositories: Spring Data JPA interfaces (e.g., `AppointmentRepository`, `PatientRepository`, `DepartmentRepository`)
- Services: business logic (e.g., `AppointmentService`, `PatientService`, `AdminUserManagementService`)
- Controllers: REST endpoints (e.g., `AppointmentController`, `AdminController`)
- Security: Spring Security configuration with JWT/OAuth2 hooks (`WebSecurityConfig`, `JwtAuthFilter`, `OAuth2SuccessHandler`) and permission types defined in `PermissionType`
- Tests: JUnit 5 integration tests (e.g., `PatientTest`, `InsuranceTest`)

## Tech stack

- Java (recommended 17+)
- Spring Boot (Web, Data JPA, Security, OAuth2)
- Spring Data JPA + Hibernate
- ModelMapper
- Lombok
- JUnit 5
- Maven (build)
- H2 / RDBMS (configurable via properties)

## Notable files & locations

- Application: `src/main/java/.../HospitalManagementApplication.java`
- Security config: `src/main/java/.../Security/WebSecurityConfig.java`
- Entities: `src/main/java/.../entity/` (Doctor, Patient, Appointment, Department, User, Permission)
- Repositories: `src/main/java/.../repository/`
- Services: `src/main/java/.../service/`
- Controllers: `src/main/java/.../Controller/` (API endpoints)
- ModelMapper config: `src/main/java/.../ModelMapper/MapperConfig.java`
- Tests: `src/test/java/.../`

## Features

- Create & manage appointments (service + controller)
- Reassign appointments to a different doctor (service method with authorization)
- Department management and doctor associations
- Permission and role model (RoleType, PermissionType)
- DTOs for safe API responses and ModelMapper-based mapping
- Example integration tests exercising services and repositories

## Getting started — run locally

Prerequisites:
- Java 17+ (or Java 11 if you prefer, but recommended 17)
- Maven 3.6+
- Optional: Docker (if you run a DB container)

1. Clone repo
   git clone https://github.com/adityayadavms/Hospital_Management_System.git
   cd Hospital_Management_System

2. Build
   mvn clean package

3. Run
   mvn spring-boot:run
   OR
   java -jar target/<artifact>.jar

4. (Optional) Run tests:
   mvn test

## Example application.properties 

Add `src/main/resources/application.properties` :

spring.datasource.url=jdbc:h2:mem:hospitaldb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
server.port=8080

Notes:
- For production use, switch to a persistent DB (PostgreSQL / MySQL) and provide secure credentials.
- The repository contains a `AppConfig` class with an example in-memory user setup (admin / pass and patient / pass) — uncomment or adapt if you want simple local auth during development.

## Security & auth

- `WebSecurityConfig` contains the security configuration. The project has hooks for JWT and OAuth2 integration (`JwtAuthFilter`, `OAuth2SuccessHandler`) and uses permission strings defined in `PermissionType`.
- Some controller methods and service methods are annotated with `@PreAuthorize` (e.g., appointment creation/reassignment).
- For local testing, you can:
  - Use in-memory auth by enabling the `UserDetailsService` example in `AppConfig`
  - Or implement simple JWT token generation to authorize requests

## Sample API usage

- Create appointment (POST)
  POST /api/appointments?doctorId={doctorId}&patientId={patientId}
  Body: AppointmentDto JSON
  Authorization: Bearer <token> (needs `appointment:write` authority)

- Reassign appointment (PUT)
  PUT /api/appointments/{appointmentId}/reassign?doctorId={doctorId}
  Authorization: Bearer <token> (needs `appointment:write` or authorized doctor)

- Admin endpoints are mounted under `/admin` (see `AdminController`) and require admin permissions.

(Refer to controller classes for full list of endpoints and DTO formats)

## Running with a real DB (Postgres SQL example)

Example `application.properties` snippet:

spring.datasource.url=jdbc:postgres://localhost:3306/hospital_db
spring.datasource.username=youruser
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

Create the DB before running:
CREATE DATABASE hospital_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

## Tests

- Integration tests under `src/test/java` demonstrate repository/service usage:
  - `PatientTest` — patient repository/service tests, paging examples
  - `InsuranceTest` — insurance assignment/dissociation and appointment creation flows

Run tests:
mvn test
