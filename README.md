# Spring Boot Skeleton / Template for Any Project

This is a Spring Boot project skeleton containing only the minimal configurations.  
No business logic is included, making it suitable as a starting point for new projects.

---

## 1. Configuration Overview

- **JPA**: Spring Data JPA

    - `spring.jpa.hibernate.ddl-auto = none`
    - `spring.jpa.open-in-view = false`
    - Default batch fetch size and common JPA optimization settings included
    - JPA Auditing enabled for common entity timestamp management
    - Soft delete support with BaseEntity abstraction

- **JSON**: Jackson

    - Configurations for safe serialization of lazy-loaded entities
    - Common JSON response structure with `ApiResult`

- **QueryDSL**: Type-safe query framework for JPA

    - Used for complex and dynamic JPA queries
    - Custom repositories implemented using `JPAQueryFactory`
    - Explicit fetch strategies applied for `open-in-view = false`
    - Optional-based query handling for null safety
    - Q classes are generated under `src/main/generated`

- **Security**: Spring Security + JWT

    - JWT-based authentication and authorization
    - AccessToken + RefreshToken authentication strategy
    - Custom JWT authentication/login/logout filters
    - Refresh token reissue and invalidation flow
    - Custom `UserDetailsService` implementation
    - Redis-backed JWT session/token validation
    - Unified authentication success/failure response structure

- **Redis**

    - RedisTemplate configuration for auth/session cache management
    - Refresh token persistence and renewal support
    - Redis-based authentication session cache repository

- **Swagger / OpenAPI**

    - Swagger UI and OpenAPI configuration
    - JWT authentication guide documentation
    - Mock APIs for filter-based authentication endpoints
    - Swagger tags for API grouping

- **YML-based Settings**

    - Profiles, groups, and logging configurations
    - `spring.profiles.group` used for environment grouping
    - `decorator.datasource.exclude-beans` applied for multi-datasource control


- **Utilities**

    - Cookie utility for refresh token handling
    - Common JSON response utility for security filters
    - Common exception handling with `BaseException` and `ExceptionHandler`
Can be integrated with Prometheus, Grafana, or other monitoring tools
Collects metrics such as JVM memory, request counts, response times, etc.
---

## 2. Progress / Changelog

| Date       | Description |
|------------|-------------|
| 2026-01-24 | Initial skeleton setup with YML configurations |
| 2026-01-27 | Add BaseEntity with common audit fields |
| 2026-01-28 | Add Swagger,Querydsl Configuration and Common Enum Serialization and Conversion Support |
| 2026-01-29 | Add JPA Converter for enum and boolean types |
| 2026-01-30 | Add base enum and interface for API success/failure messages |
| 2026-01-31 | Add CustomException with ExceptionHandler , Common ApiResult Response |
| 2026-05-07 | Implement user create/delete logic with soft delete and enable JPA auditing |
| 2026-05-07 | Configure Redis templates and repositories for auth/session cache management |
| 2026-05-07 | Implement JWT-based authentication and Redis-backed JWT request validation |
| 2026-05-07 | Configure QueryDSL, application beans, CORS, and Swagger settings |
| 2026-05-11 | Extract cookie utility and add refresh token authentication exception handling |
| 2026-05-11 | Add authentication response DTOs and implement JWT login filter |
| 2026-05-11 | Implement refresh token persistence, renewal, and Redis token management |
| 2026-05-12 | Unify login response structure and separate refresh token validation logic |
| 2026-05-12 | Implement custom UserDetailsService and Optional-based user lookup handling |
| 2026-05-12 | Refactor refresh token response handling using enum-based status |
| 2026-05-12 | Configure JWT authentication filter chain, logout filter, and refresh token reissue API |
| 2026-05-12 | Improve Swagger authentication documentation and add mock APIs for filter-based authentication |


---

## 3. Getting Started (Optional)

1. Clone the project:
```bash
git clone https://github.com/KangWooJu/SpringDefaultSettings
