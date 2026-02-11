# E-Commerce API – Spring Boot

A production-style RESTful e-commerce backend built with **Spring Boot**, focusing on clean architecture, domain modeling, payments, security, and real-world deployment practices.

This project demonstrates how a typical online store backend can be structured with authentication, role-based authorization, Stripe payments, event-driven updates, pagination, validation, and Docker deployment.

---

## Tech Stack

- Java 25
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Stripe API
- MapStruct
- Docker & Docker Compose
- Maven

---

## Main Features

### Authentication & Authorization
- JWT **Access + Refresh Tokens**
- Stateless authentication
- Role-Based Access Control (RBAC)
- Admin vs Regular User endpoints separation
- Secure password handling
- Custom `UserDetails` and JWT filter

---

### Products & Categories
- CRUD operations
- Enable / Disable instead of hard delete
- Product inventory tracking (`balance`)
- Average selling price derived from purchase history
- Category management
- Pagination and sorting support

---

### Cart
- One cart per user
- Add / remove / update items
- Automatic total cost calculation
- Cart cleared after checkout

---

### Orders
- Order creation from cart
- Order status lifecycle:
    - `CREATED`
    - `PAYMENT_PENDING`
    - `PAID`
    - `PAYMENT_FAILED`
- Snapshot of product data stored in order items
- Order items contain:
    - productId
    - name
    - price at time of purchase
    - quantity

---

### Payments (Stripe Integration)
- Stripe **Checkout Sessions**
- Webhook handling for:
    - `payment_intent.succeeded`
    - `payment_intent.payment_failed`
- Metadata usage to map Stripe events to internal payments
- Payment status tracking
- Payment linked to order
- Idempotent handling to avoid duplicate processing

---

### Purchases & Inventory
- Purchases increase product balance
- Average selling price recalculated automatically
- **Domain Events** triggered on purchase creation
- Event listeners update product statistics asynchronously

---

## Architecture Decisions

### Product Balance Stored in Product
Inventory (`balance`) is stored directly in the `Product` entity to:
- Avoid expensive joins
- Allow fast reads
- Support event-driven updates

---

### Rich Domain Models
Entities contain **behavior related only to themselves**, for example:
- `Product.decreaseBalance()`
- `Cart.getTotalCost()`
- `Order.markPaid()`

Business logic that affects multiple aggregates is handled in services.

---

### Event-Driven Updates
- Purchases trigger `PurchaseCreatedEvent`
- Listener updates:
    - total purchase price
    - total purchase quantity
    - average selling price
    - product balance
- Uses transactional event listeners and optimistic locking

---

## Checkout Flow

1. User initiates checkout
2. Cart is validated
3. Order is created from cart
4. Payment entity is created
5. Stripe checkout session is generated
6. Cart is cleared
7. Order status becomes `PAYMENT_PENDING`
8. Stripe webhook:
    - On success → order marked `PAID`, inventory deducted
    - On failure → order marked `PAYMENT_FAILED`

---

## Database Design

### Core Tables
- users
- roles
- permissions
- products
- categories
- carts
- cart_items
- orders
- order_items
- payments
- purchases

### Design Principles
- UUID primary keys
- Soft deletes via `isEnabled`
- Snapshotting product info in orders
- Foreign keys for integrity
- Event-based inventory updates

---

## Validation

- Bean Validation annotations (`@NotBlank`, `@Range`, `@Email`, etc.)
- Custom enum validators
- Defensive domain checks inside entities
- Centralized exception handling

---

## Pagination & Sorting

- Pageable endpoints
- Custom paging options
- Enum-based sorting fields
- Case-insensitive enum conversion

---

## Security

- JWT Access & Refresh tokens
- Stateless sessions
- Custom JWT filter
- Role and permission checks
- Public vs protected endpoints
- Admin-only operations

---

## Deployment

- Dockerized application
- Docker Compose with PostgreSQL
- Environment variable configuration
- Deployed to a cloud platform
- External managed PostgreSQL database

---

## Screenshots

_Add screenshots here:_
- Swagger UI
- Login / JWT flow
- Checkout process
- Stripe webhook logs
- Docker running containers
- Database dashboard

---

## Possible Future Improvements

- Email notifications
- Message queues (RabbitMQ / Kafka)
- Identity Server / OAuth2
- Frontend client
- Integration tests with Testcontainers
- Advanced analytics dashboards

---

## How to Run Locally

### Using Docker
```bash
docker-compose up --build
