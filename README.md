# Spring Chat Practice

This is a Spring Boot project for practicing real-time chat systems using WebSocket.

The project is based on my Spring Boot default template and is intended for studying WebSocket communication, session management, concurrency, testing, and scalable chat server architecture.

---

## 1. Configuration Overview

- **JPA**: Spring Data JPA

  - `spring.jpa.hibernate.ddl-auto = none`
  - `spring.jpa.open-in-view = false`
  - Default batch fetch size and common JPA optimization settings included
  - JPA Auditing enabled for common entity timestamp management
  - Soft delete support with `BaseEntity` abstraction

- **JSON**: Jackson

  - Configurations for safe serialization of lazy-loaded entities
  - Common JSON response structure with `ApiResult`

- **QueryDSL**

  - Type-safe query framework for JPA
  - Custom repositories implemented using `JPAQueryFactory`
  - Explicit fetch strategies applied for `open-in-view = false`
  - Optional-based query handling for null safety
  - Bulk update and delete operations using QueryDSL
  - Q classes generated under `src/main/generated`

- **Security**

  - Spring Security with JWT authentication
  - Access Token and Refresh Token authentication strategy
  - Custom JWT login, authentication, and logout filters
  - Redis-backed Refresh Token validation
  - Custom `UserDetailsService`
  - `AuthPrincipal`-based authentication support
  - Unified authentication success and failure responses

- **WebSocket / STOMP**

  - STOMP over WebSocket communication
  - JWT authentication during STOMP connection
  - Custom `ChannelInterceptor`
  - Chat room membership validation
  - System message publishing for room enter and leave events
  - Message routing based on destination and message type

- **Redis**

  - RedisTemplate configuration
  - Refresh Token persistence
  - Authentication cache repository
  - User authentication cache using `UserAuthCache`

- **Swagger / OpenAPI**

  - Swagger UI and OpenAPI configuration
  - JWT authentication guide
  - Mock APIs for authentication filters
  - API grouping with Swagger Tags
  - SpringDoc OpenAPI v3

- **Testing**

  - JUnit 5
  - Mockito
  - Testcontainers
  - MySQL integration testing
  - Redis integration testing
  - Controller slice tests
  - Service unit tests
  - QueryDSL repository tests
  - Reusable `IntegrationTest` base class

- **YML-based Settings**

  - Environment profile grouping
  - Logging configuration
  - Multi-datasource configuration
  - `spring.profiles.group` for grouped environment settings
  - `decorator.datasource.exclude-beans` for datasource control

- **Utilities**

  - Cookie utility for Refresh Token handling
  - Common JSON response utility for security filters
  - Global exception handling with `BaseException`
  - Monitoring-ready structure for Prometheus and Grafana

---

## 2. Progress / Changelog

| Date | Description |
|------|-------------|
| **2026-07-15** | Upload default package |
| | Initialize project structure |
| **2026-07-16** | Implement Chat domain entities, repositories, services, and controllers |
| | Add STOMP/WebSocket configuration |
| | Implement QueryDSL repositories and generate Q classes |
| | Add request and response DTOs for chat APIs |
| | Introduce Facade layer for integration services |
| | Refactor `BaseEntity` timestamp type to `LocalDateTime` |
| | Add `WebSocketException` and exception handling |
| | Add nickname duplication check API |
| | Remove unused Google API configuration |
| **2026-07-17** | Add STOMP message publishing flow |
| | Introduce `SendChatMessageRequest` DTO |
| | Refactor chat response DTO structure |
| | Integrate `ChatFacade` into the message publishing flow |
| | Refactor STOMP controller using `@Payload` and `Principal` |
| | Add JWT authentication provider for STOMP connection validation |
| | Introduce STOMP `ChannelInterceptor` |
| | Validate chat room membership using QueryDSL |
| | Add system message publishing for room enter and leave events |
| | Parse STOMP messages based on destination and message type |
| **2026-07-23** | Introduce Testcontainers-based integration test environment for MySQL and Redis |
| | Add reusable `IntegrationTest` base class |
| | Implement `UserAuthCache` and Redis-backed authentication cache |
| | Introduce `UserAuthCacheService` |
| | Cache authenticated user information during JWT login |
| | Configure `DaoAuthenticationProvider` for the custom `UserDetailsService` |
| | Add `AuthPrincipal`-based authentication support |
| | Add integration and unit tests for User and Security components |
| | Add Redis repository tests |
| | Refactor User entity and DTOs to support `nickname` |
| | Upgrade SpringDoc OpenAPI dependency |
| | Configure JWT secret through environment variables |
| **2026-07-27** | Reorganize the test package structure |
| | Add reusable helper classes for chat domain repository tests |
| | Add Lombok test dependency for test helper classes |
| | Add `@Getter` and apply Lombok utilities to test fixtures |
| | Add QueryDSL repository tests for the Chat domain |
| | Add tests for ChatRoom, ChatRoomMember, and ChatMessage query logic |
| | Replace the ChatRoomMember service deletion method with a QueryDSL bulk delete operation |
| | Add bulk deletion methods to the QueryDSL repository and Query Service |
| | Refactor the ChatRoomMember hard delete flow |
| | Prevent soft-deleted chat rooms from being returned by queries |
| | Fix the null check in `ChatSubscriptionRegistry.disconnect()` |
| | Add unit tests for Chat domain services |
| | Add controller tests for Chat domain APIs |
| **2026-07-28** | Separate ChatRoomMember responsibilities from `ChatRoomService` |
| | Introduce a dedicated `ChatRoomMemberService` |
| | Implement APIs for adding and removing chat room members |
| | Add `ChatRoomMemberController` |
| | Add request DTOs for adding and removing chat room members |
| | Add query methods to find a User by ID |
| | Add query methods to find a ChatRoomMember by member ID |
| | Add corresponding Query Service methods |
| | Extend `ChatFacade` with chat room member management operations |
| | Refactor `ChatFacadeTest` for the newly added APIs |
| | Add unit tests for `ChatRoomMemberService` |
| | Add controller tests for `ChatRoomMemberController` |
| | Adjust existing tests after separating service responsibilities |
| | Remove unused libraries and beans |
| **2026-07-30** | Refactor STOMP message validation flow |
| | Separate subscription and message send validation logic |
| | Improve STOMP session and channel interceptor logging |
| | Add Swagger documentation for Chat REST APIs |
| | Configure WebSocket handshake endpoints for integration tests |
| | Add STOMP/WebSocket integration test support |
| | Split Chat integration tests into REST and WebSocket test suites |
| | Introduce reusable STOMP client test helper |
| | Add Swagger mock APIs for STOMP messaging documentation |
| **2026-07-31** | Refactor STOMP message validation flow |
| | Separate subscription and message send validation logic |
| | Improve STOMP session and channel interceptor logging |
| | Add Swagger documentation for Chat REST APIs |
| | Configure WebSocket handshake endpoints for integration tests |
| | Add STOMP/WebSocket integration test support |
| | Split Chat integration tests into REST and WebSocket test suites |
| | Introduce reusable STOMP client test helper |
| | Add Swagger mock APIs for STOMP messaging documentation |

---

## 3. Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/KangWooJu/SPRING_CHAT_PRACTICE.git
```

### 2. Configure environment variables

Configure the following values:

- JWT secret
- Database connection
- Redis connection
- Spring profiles
- `application.yml`

### 3. Run the application

```bash
./gradlew bootRun
```

---

## 4. Current Features

### Authentication

- JWT login
- JWT logout
- Access Token and Refresh Token management
- Redis authentication cache
- `AuthPrincipal`-based authentication
- Custom authentication filters

### Chat

- Chat room creation and deletion
- Chat room member management
- STOMP message publishing
- Chat message persistence
- System messages for enter and leave events
- JWT-based WebSocket authentication
- Chat room membership validation

### Persistence

- Spring Data JPA
- QueryDSL
- Bulk update and delete operations
- Soft delete
- MySQL

### Testing

- Integration tests
- Unit tests
- Controller slice tests
- QueryDSL repository tests
- MySQL and Redis Testcontainers
- Reusable test fixtures and helper classes

---

## 5. Project Goals

- Learn WebSocket and STOMP communication
- Understand WebSocket session lifecycle management
- Implement chat room and membership architecture
- Practice concurrent programming and shared state management
- Persist chat messages using JPA
- Apply QueryDSL to chat domain queries and bulk operations
- Build reliable unit, controller, repository, and integration tests
- Prepare for scalable and distributed chat server architecture

---

## 6. Future Goals

- Redis Pub/Sub
- RabbitMQ integration
- Kafka integration
- Distributed WebSocket architecture
- Chat message search
- Load testing
- Performance optimization
- Rate limiting
- Monitoring with Prometheus and Grafana
- AI Agent integration