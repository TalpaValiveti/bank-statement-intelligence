# Bank Statement Intelligence

A cloud-native microservices platform to upload, analyze, and monitor bank statements using Spring Boot and OCI AI.

## Services

| Service | Port | Description |
|---|---|---|
| api-gateway | 8080 | Routes all requests |
| auth-service | 8081 | Login, register, JWT |
| statement-service | 8082 | File upload and storage |
| ai-service | 8083 | OCR, categorization, anomaly detection |
| alert-service | 8084 | Email alerts |

## Quick Start

### Run locally with Docker Compose
```bash
docker-compose up --build
```

### Run individually
```bash
mvn clean compile
cd auth-service && mvn spring-boot:run
```

## Health Checks

| Service | Health URL |
|---|---|
| api-gateway | http://localhost:8080/actuator/health |
| auth-service | http://localhost:8081/actuator/health |
| statement-service | http://localhost:8082/actuator/health |
| ai-service | http://localhost:8083/actuator/health |
| alert-service | http://localhost:8084/actuator/health |

## Upload a Statement
```bash
curl -X POST http://localhost:8080/api/statements/upload \
  -F "file=@/path/to/statement.pdf" \
  -F "userId=1"
```

## Tech Stack
- Java 17 + Spring Boot 3.x
- Spring Cloud Gateway
- PostgreSQL 15 + Flyway
- Docker + Docker Compose
- OCI AI Services (Sprint 3)
- React Frontend (Sprint 4)

## Sprint Progress
- Sprint 1 - Foundation (Days 1-5) - COMPLETE
- Sprint 2 - Security (Days 6-10)
- Sprint 3 - OCI AI (Days 11-15)
- Sprint 4 - Frontend + Deploy (Days 16-20)
