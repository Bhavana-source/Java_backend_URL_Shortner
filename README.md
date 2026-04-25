# 🔗 URL Shortener System (Scalable Backend with Redis & Kafka)

A production-grade backend system to generate and manage short URLs, designed with **caching** and **event-driven architecture** for high performance and scalability.

---

## 🚀 Overview

This project demonstrates how to design a scalable backend system by combining:

- Fast data access using **Redis caching**
- Asynchronous processing using **Apache Kafka**
- Clean architecture using **Spring Boot**

---

## ✨ Features

- 🔗 Generate short URLs from long URLs
- 🧩 Custom alias (user-defined short codes)
- 🔁 URL redirection to original link
- ⏳ URL expiry handling
- 🚫 Soft delete using `isActive`
- 📊 Analytics API (click count, last accessed time)
- ⚠️ Global exception handling & validation
- 📘 Swagger API documentation

---

## ⚡ Performance & Scalability

### 🔴 Redis (Caching)
- Implemented **cache-aside pattern**
- Reduces database load for frequently accessed URLs
- Improves response time during redirection

### 🟠 Apache Kafka (Event-Driven Architecture)
- Click tracking handled asynchronously
- Decouples redirect flow from analytics updates
- Improves system scalability and responsiveness

---

## 🏗️ System Architecture
Client
↓
Controller
↓
Service
↓
Redis Cache → (Cache Miss) → Database (PostgreSQL)
↓
Redirect Response

(Async Flow)
Service → Kafka Producer → Kafka Topic → Kafka Consumer → Database (Analytics Update)


---

## 🔄 Request Flow

### 1. Create Short URL

POST /shorten

- Generates a unique short code or uses custom alias
- Stores mapping in database

---

### 2. Redirect

GET /{shortCode}

- Checks Redis cache first
- Falls back to DB if not found
- Sends redirect response
- Publishes click event to Kafka (async)

---

### 3. Analytics

GET /urls/{shortCode}/stats

- Returns click count, creation time, expiry, and usage insights

---

### 4. Deactivate URL

PATCH /urls/{shortCode}/deactivate

- Soft delete using `isActive = false`

---

## 🧠 Design Decisions

- **Cache-Aside Pattern** for Redis (better control over cache consistency)
- **Event-Driven Architecture** using Kafka for non-blocking analytics updates
- **Soft Delete** instead of hard delete to preserve analytics data
- **Layered Architecture** (Controller → Service → Repository)
- **DTO-based API design** to separate internal models from external responses

---

## 🛠️ Tech Stack

- Java, Spring Boot
- PostgreSQL
- Redis
- Apache Kafka
- JPA / Hibernate
- Swagger (OpenAPI)

---

## 📊 Example API

### Create Short URL
```json
POST /shorten
{
  "originalUrl": "https://google.com",
  "customCode": "google123"
}
Response
{
  "shortCode": "google123",
  "shortUrl": "http://localhost:8080/google123"
}

▶️ How to Run

Clone the repository
Start dependencies:
PostgreSQL
Redis
Kafka
Configure application.properties
Run the Spring Boot application

Access Swagger UI:
http://localhost:8080/swagger-ui.html

📈 Future Improvements

Rate limiting for API protection
User authentication & authorization
Distributed deployment (Docker/Kubernetes)
Analytics dashboard UI
Multi-node Kafka setup

👤 Author
Bhavana
