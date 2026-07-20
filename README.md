# Spring Chat Practice

This is a Spring Boot project for practicing real-time chat systems using WebSocket.

The project is based on my Spring Boot default template and is intended for studying WebSocket communication, session management, concurrency, and chat architecture.

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
    - Can be integrated with Prometheus, Grafana, or other monitoring tools
    - Collects metrics such as JVM memory, request counts, response times, etc.

---

## 2. Progress / Changelog

| Date       | Description |
|------------|-------------|
| 2026-07-15 | upload default package |
| 2026-07-16 | Implement Chat domain (Entity, Repository, Service, Controller) |
|            | Add STOMP/WebSocket configuration |
|            | Implement QueryDSL repositories and generate Q classes |
|            | Add request/response DTOs for chat APIs |
|            | Introduce Facade layer for integration services |
|            | Refactor `BaseEntity` timestamp type to `LocalDateTime` |
|            | Add `WebSocketException` and exception handling |
|            | Add nickname duplication check API |
|            | Remove unused Google API configuration |
| 2026-07-17 | Add message publishing flow for STOMP chat |
|            | Introduce `SendChatMessageRequest` DTO |
|            | Refactor chat response DTO structure |
|            | Integrate `ChatFacade` into message publishing flow |
|            | Refactor STOMP controller method signature with `@Payload` and `Principal` |
|            | Add JWT authentication provider for STOMP connection validation |
|            | Introduce STOMP `ChannelInterceptor` for JWT and chat room membership validation |
|            | Implement QueryDSL validation for chat room subscriber membership |
|            | Add system message publishing API for room enter and leave events |
|            | Implement STOMP message parsing by destination and message type |

---

## 3. Getting Started 

1. Clone the project:

```bash
git clone https://github.com/KangWooJu/SPRING_CHAT_PRACTICE.git
```

2. Configure your environment variables and `application.yml`.

3. Run the application.

---

## 4. Project Goal

- Learn WebSocket communication
- Understand session lifecycle management
- Implement chat room architecture
- Practice concurrent programming with `ConcurrentHashMap`
- Persist chat history using JPA
- Build a scalable chat server architecture
- Prepare for future extensions such as STOMP, Redis Pub/Sub, Kafka, and AI Agent integration
