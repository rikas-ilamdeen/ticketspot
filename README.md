# TicketSpot

TicketSpot is a single-event ticketing application. Vendors add tickets to the configured event, customers purchase available tickets, and connected clients receive event updates over WebSocket.

## Stack

- Backend: Java 17, Spring Boot 3.3.5, Spring Data JPA, PostgreSQL
- Frontend: Angular 19.0.x, Angular CLI 19.0.4, TypeScript 5.6.2
- UI: Bootstrap 5.3.3 and Bootstrap Icons 1.11.3
- Messaging: STOMP over SockJS with Spring's in-memory broker

## Prerequisites

- Java 17 or newer
- Node.js and npm compatible with Angular 19
- PostgreSQL
- Chrome or another browser supported by the Angular test runner

## PostgreSQL Setup

Create a database named `ticketing_system` and make it available on the local PostgreSQL port used by the application:

```sql
CREATE DATABASE ticketing_system;
```

The default connection is `jdbc:postgresql://localhost:5433/ticketing_system` with username `postgres` and password `admin`, matching the original local coursework setup. Configure another database without editing source files by setting `TICKETSPOT_DB_URL`, `TICKETSPOT_DB_USERNAME`, and `TICKETSPOT_DB_PASSWORD`.

## Run The Application

Start the backend from `ticketing-backend`:

```powershell
cd ticketing-backend
.\mvnw.cmd spring-boot:run
```

Start the frontend in another terminal:

```powershell
cd ticketing-frontend
npm install
npm start
```

Open `http://localhost:4200/`. The frontend expects the backend at `http://localhost:8080`.

The legacy interactive ticket configuration CLI is disabled by default. Enable it manually with `ticketspot.cli.enabled=true` when needed.

## API And WebSocket Overview

Current HTTP endpoints include customer and vendor signup/login, `GET /api/event`, ticket purchase at `POST /api/customer/buy`, ticket release at `POST /api/vendor/addTickets`, and customer/vendor get, update, list, and delete operations.

Ticket quantities must be positive. Purchases may consume exactly the remaining inventory. The backend publishes event updates to `/topic/event` through the STOMP endpoint `/ws`; the frontend connects using SockJS.

## Tests And Build

```powershell
cd ticketing-backend
.\mvnw.cmd test

cd ..\ticketing-frontend
npm test -- --watch=false --browsers=ChromeHeadless
npm run build
```

## V0, V1, And V2

- V0 is the archived original coursework baseline, tagged `v0-coursework-final`.
- V1 preserves the original Spring Boot, Angular, database, lock, and WebSocket architecture while correcting startup, validation, authentication, test, configuration, and documentation issues.
- V2 is reserved for JWT authorization, multiple events, distributed concurrency, payments, Docker, CI/CD, Azure, Redis, brokers, and microservices.

## Current V1 Limitations

- The application supports one event/ticket configuration.
- Authentication is basic email/password login without JWT or authorization roles.
- Ticket concurrency is protected only within one backend process.
- PostgreSQL schema setup remains development-oriented and uses Hibernate's `update` mode.
- Payment processing and deployment automation are not implemented.

