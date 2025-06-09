# Ops. Visión Real ERP

## Concepts

- **JDBC**: Java API that provides a standard way for Java programs to interact with relational databases.
  - Spring Data JDBC provide repository support for JDBC.
- **R2DBC**: Reactive programming APIs to relational databases.
  - Spring Data R2DBC provide repository support for R2DBC.
- **JPA**: JPA (Java Persistence API) is a specification, not a framework, that defines how to map Java objects to relational databases
- **ORM**: An ORM, or Object Relational Mapper, is a piece of software designed to translate between the data representations used by databases and those used in object-oriented programming.
  - Hibernate is and implementation of JPA, but Spring Data JPA add more details to the implementation.

---

- **Hexagonal Architecture**: Focuses on decoupling the core business from implementation details, making it easier to replace and evolve the technical infrastructure over time.

## Scripts

- `docker run -d -p <HOST_PORT>:<CONTAINER_PORT> -e SPRING_PROFILE=<profile> opvreal/<microservice>:1.0.0`
  - HOST_PORT: The port number on your host machine where you want to receive traffic.
  - CONTAINER_PORT: The port number within the container that's listening for connections.
 
- `docker run -it opvreal/<microservice>:1.0.0 /bin/bash`
