# 🚀 URL Shortener (Spring Boot)

A scalable URL shortener built using Java, Spring Boot, and PostgreSQL.
This application allows users to convert long URLs into short links and redirects users to the original URL when accessed.

## ✨ Features
🔗 Create short URL from long URL
🔁 Redirect short URL to original URL
♻️ Duplicate URL handling (returns existing short URL)
📊 Click count tracking (if implemented)
⚡ Clean layered architecture (Controller, Service, Repository)

## 🏗️ Tech Stack
Java 17
Spring Boot
Spring Data JPA
PostgreSQL
Maven

## 📌 API Endpoints
### 1. Create Short URL

POST /api/v1/urls

Request:

{
  "originalUrl": "https://www.google.com"
}

Response:

{
  "originalUrl": "https://www.google.com",
  "shortCode": "Ab12Xy",
  "shortUrl": "http://localhost:8080/api/Ab12Xy"
}
### 2. Redirect to Original URL

GET /{shortCode}

Example:

http://localhost:8080/Ab12Xy

Redirects to:

https://www.google.com

## 🗄️ Database Schema

Table: short_urls

Column	Description
id	Primary key
original_url	Original long URL
short_code	Unique short code
created_at	Creation timestamp
click_count	Number of clicks
is_active	Status flag

## ⚙️ How to Run Locally
Clone the repository
git clone https://github.com/YOUR_USERNAME/springboot-url-shortener.git
Configure PostgreSQL in application.properties
Run the application
mvn spring-boot:run
Test APIs using Postman

## 📈 Future Improvements
JWT Authentication
Redis caching
URL expiry support
Docker containerization
Deployment to cloud (AWS / Render / Railway)
Analytics dashboard

## Author
Gangarapu Bhavana
