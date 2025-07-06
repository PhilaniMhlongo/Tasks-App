
---

# 📌 Task Tracker REST API - Spring Boot

A simple RESTful API for managing tasks, built with **Spring Boot**. This backend service allows users to create, read, update, and delete tasks via HTTP endpoints.

---

## 📁 Project Structure

```
task-tracker/
├── src/
│   └── main/
│       ├── java/com/example/tasktracker/
│       │   ├── controller/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── service/
│       │   └── TaskTrackerApplication.java
│       └── resources/
│           └── application.properties
├── pom.xml
└── README.md
```

---

## 🚀 Features

* ✅ Create new tasks
* 📋 Get all or single task
* ✏️ Update a task
* ❌ Delete a task
* 🔍 Filter tasks by status (e.g. `PENDING`, `COMPLETED`)
* 📆 Sort by due date

---

## ⚙️ Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* H2 / PosgresSQL (configurable)
* Maven

---

## 🏁 Getting Started

### 🔧 Prerequisites

* Java 21
* Maven

### 📦 Installation

```bash
git clone https://github.com/PhilaniMhlongo/Tasks-App.git
cd task-tracker
mvn clean install
```

### ▶️ Run the application

```bash
mvn spring-boot:run
```

> By default, it runs on `http://localhost:8080`

---

## 📌 API Endpoints

### 🔹 Create a task

**POST** `/api/tasks`

```json
{
  "title": "Buy groceries",
  "description": "Milk, Eggs, Bread",
  "dueDate": "2025-07-10",
  "status": "PENDING"
}
```

---

### 🔹 Get all tasks

**GET** `/api/tasks`

**Optional query params:**

* `status=PENDING`
* `sort=dueDate`

---

### 🔹 Get task by ID

**GET** `/api/tasks/{id}`

---

### 🔹 Update a task

**PUT** `/api/tasks/{id}`

```json
{
  "title": "Buy groceries and fruit",
  "description": "Milk, Eggs, Bread, Apples",
  "dueDate": "2025-07-11",
  "status": "IN_PROGRESS"
}
```

---

### 🔹 Delete a task

**DELETE** `/api/tasks/{id}`

---

## 🧪 Testing

Run unit and integration tests:

```bash
mvn test
```

---


Pull requests are welcome. For major changes, please open an issue first to discuss.

---

Let me know if you'd like it customized to your actual project (e.g. if you're using PostgreSQL, JWT auth, Swagger docs, etc.).
