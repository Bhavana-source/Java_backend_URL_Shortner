# 🔗 URL Shortener System

A scalable backend system to generate and manage short URLs, built using Java and Spring Boot.  
Designed with caching and event-driven architecture for performance and scalability.

---

## 🚀 Features

- Generate short URLs for long links
- Custom alias support (user-defined short codes)
- URL redirection to original link
- URL expiry handling
- Soft delete using isActive flag
- Analytics API (click count, last accessed time)
- Global exception handling & validation
- Swagger API documentation

---

## ⚡ Performance & Scalability

- **Redis Caching** (cache-aside pattern) to reduce DB load
- **Apache Kafka Integration** for asynchronous click analytics
- Optimized database queries using PostgreSQL

---

## 🏗️ Architecture

User Request → Spring Boot API → Redis Cache → PostgreSQL  
                ↓  
               Kafka → Consumer → DB (Analytics)

---

## 🛠️ Tech Stack

- Java, Spring Boot
- PostgreSQL
- Redis
- Apache Kafka
- JPA / Hibernate
- Swagger

---

## 📌 API Endpoints

| Method | Endpoint | Description |
|--------|--------|-------------|
| POST | /shorten | Create short URL |
| GET | /{shortCode} | Redirect to original URL |
| GET | /urls/{shortCode}/stats | Get analytics |
| PATCH | /urls/{shortCode}/deactivate | Deactivate URL |

---

## ▶️ How to Run

1. Clone the repo  
2. Start PostgreSQL, Redis, Kafka  
3. Run Spring Boot application  
4. Open Swagger UI  

---

## 📊 Future Improvements

- Rate limiting
- User authentication
- Dashboard for analytics
- Distributed deployment

---

## 👤 Author

Bhavana
