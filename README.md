# 🚚 Transport Management System (TMS) – Spring Boot + PostgreSQL

This project is a backend system for managing **Transporters, Loads, Bids, and Bookings**.  
It is built using **Spring Boot**, **PostgreSQL**, and **RESTful API architecture**.

The system supports load posting, bidding by transporters, automated best-bid suggestions, and booking generation with concurrency-safe truck allocation.

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 4.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**
- **Postman (for API testing)**


---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/tms-backend.git
cd tms-backend


```
### 2️⃣ Configure PostgreSQL
```bash
CREATE DATABASE tms;
```
### 3️⃣ Configure application.properties
```bash

spring.datasource.url=jdbc:postgresql://localhost:5432/tms
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 📮 API Documentation
🔹 Postman Collection
```bash
Import the file:

postman/TMS_Postman_Collection.json

```
