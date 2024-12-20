# Onion Architecture with Spring Boot - Java Modules

This project demonstrates a modular design following the **Onion Architecture** pattern. The
application is organized into distinct layers, each encapsulating different concerns and
responsibilities. Each layer is implemented as a separate Java module.

---

## Project Structure

### Layers

1. **Domain Models (`domain-models`)**
    - **Responsibilities**:
        - Encapsulates the core business entities and data models.
        - Contains domain logic without external dependencies.

2. **Domain Services (`domain-services`)**
    - **Responsibilities**:
        - Implements business logic involving multiple domain models.
        - Provides domain-specific behavior.

3. **Application Services (`application-services`)**
    - **Responsibilities**:
        - Orchestrates use cases by calling domain services and models.
        - Coordinates between the domain and presentation layers.

4. **Presentation (`presentation`)**
    - **Responsibilities**:
        - Handles HTTP requests and responses.
        - Maps request data to application services and returns results.

5. **Infrastructure (`infrastructure`)**
    - **Responsibilities**:
        - Provides technical implementations (e.g., Spring Security, Database access).
        - Bridges internal layers and external systems.

---
