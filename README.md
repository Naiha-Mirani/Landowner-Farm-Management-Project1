# 🌾 Farm Management System
### Java OOP Project — Spring 2026

---

## 📌 Project Description

A **Farm Management System** developed in Java using Object-Oriented Programming (OOP) concepts. The system helps manage farm-related data such as crops, records, and operations efficiently using a structured and modular approach.

---

## 👤 Student Info

| Field | Details |
|---|---|
| **Name** | Naiha Mirani |
| **Student ID (CMS)** | 023-25-0084 |
| **Section** | BSCS-D |

---

## 🎯 Purpose

- Manage farm data **digitally** instead of manual records
- Apply **OOP concepts** in a real-world scenario
- Practice **Java + Database (MySQL)** connectivity

---

## 🧩 Main Features

| Module | Description |
|---|---|
| 🌱 Crop Management | Add / View / Update / Delete crops |
| 📊 Data Storage | MySQL Database integration |
| 🧠 Structured Classes | Model, Database, Logic separation |
| 🖥️ Console UI | Console-based user interface |

---

## 📁 Project Structure

```
FarmSystem/
├── src/
│   ├── Mainnn.java                   ← Entry point (main method)
│   ├── database/
│   │   └── DBConnection.java         ← Singleton DB connection
│   ├── models/
│   │   ├── Person.java               ← Abstract base class (OOP)
│   │   ├── Landowner.java            ← extends Person
│   │   ├── Worker.java               ← extends Person
│   │   ├── Field.java
│   │   ├── Crop.java
│   │   ├── Assignment.java
│   │   └── Payment.java
│   └── dao/                          ← Data Access Object
│       ├── LandownerDAO.java         ← CRUD for LANDOWNER table
│       ├── WorkerDAO.java            ← CRUD for WORKER table
│       ├── FieldDAO.java             ← CRUD for FIELD table
│       ├── PaymentDAO.java           ← CRUD for PAYMENT table
│       └── ReportDAO.java            ← Multi-table JOIN reports
└── lib/
    └── mysql-connector-j-9.6.x.jar  ← JDBC driver (place here)
```

---

## ⚙️ Setup & Installation

### Step 1 — Setup Database

1. Open **MySQL Workbench**
2. Create a new database: `farmSystem`
3. Go to: `Server → Data Import`
4. Select the provided `.sql` file
5. Click **Start Import**

### Step 2 — Configure DB Connection

Open `src/database/DBConnection.java` and update:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/farm_database";
private static final String USER     = "root";          // your MySQL username
private static final String PASSWORD = "yourpassword";  // your MySQL password
```

### Step 3 — Run the Project

1. Open the project in your IDE (NetBeans / IntelliJ / Eclipse)
2. Ensure the MySQL Connector (JDBC) jar is added to the build path
3. Compile and run the `Mainnn` class

---

## 🖥️ Compile & Run (Command Line)

> Run all commands from inside the `FarmSystem/` directory.

**Compile:**
```bash
javac -cp "lib\mysql-connector-j-9.6.0.jar" -d classFiles src\Mainnn.java src\database\DBConnection.java src\models\*.java src\dao\*.java
```

**Run:**
```bash
java -cp "classFiles;lib\mysql-connector-j-9.6.0.jar" Mainnn
```

> 📝 Make sure your JDBC `.jar` file is present in the `lib/` folder.

---

## 🧠 OOP Concepts Demonstrated

### 1. Encapsulation
- All model fields are `private`
- Accessed only via getters/setters
- **Files:** `Person.java`, `Landowner.java`, `Worker.java`, etc.

### 2. Inheritance
- `Person` (abstract) is the parent class
- `Landowner extends Person`
- `Worker extends Person`
- **Files:** `Person.java`, `Landowner.java`, `Worker.java`

### 3. Abstraction
- `Person` is abstract — cannot be instantiated directly
- `getDetails()` is abstract — every subclass must implement it
- **File:** `Person.java`

### 4. Polymorphism
- `Person[]` array holds both `Landowner` and `Worker` objects
- `p.getDetails()` calls the correct version at runtime
- **Demonstrated in:** `demoOOPConcepts()` in `Main.java`

### 5. Singleton Pattern
- `DBConnection` creates only one `Connection` object ever
- Same connection reused across all DAO classes
- **File:** `DBConnection.java`

### 6. Separation of Concerns
| Layer | Responsibility |
|---|---|
| `models/` | Pure data classes — no DB code |
| `dao/` | All SQL logic — no UI code |
| `Main` | All user interaction — no SQL code |

---

## 🗄️ DBMS Concepts Used

### 1. CRUD via JDBC
- `PreparedStatement` for all `INSERT`, `UPDATE`, `DELETE`
- `ResultSet` for `SELECT` queries

### 2. Primary Key & Foreign Key
- Every DAO enforces PK uniqueness
- FK violations handled with `try/catch` and helpful error messages

### 3. JOIN Queries — `ReportDAO.java`
- 4-table JOIN: `LANDOWNER → FIELD → ASSIGNMENT → WORKER`
- Aggregate functions: `SUM()`, `COUNT()`, `MAX()`
- `GROUP BY` for payment summary per worker

### 4. SQL Injection Prevention
- `PreparedStatement` used everywhere — no string concatenation

### 5. Connection Management
- Singleton `DBConnection` (one shared connection)
- `closeConnection()` called on application exit

---

## 🛠️ Technologies Used

![Java](https://img.shields.io/badge/Java-JDK%208%2B-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=flat-square&logo=mysql)
![JDBC](https://img.shields.io/badge/JDBC-Connector-green?style=flat-square)

- **Java** (JDK 8 or above)
- **MySQL** Database
- **MySQL Workbench**
- **JDBC** (Java Database Connectivity)

---

## 🔗 Project Links

| Resource | Link |
|---|---|
| 📂 GitHub Repository | https://github.com/Naiha-Mirani/Landowner-Farm-Management-Project |
| 🎥 Demo Video (YouTube) | https://youtu.be/OdcfoniwNVs |

---

## 🚨 Troubleshooting

| Error | Fix |
|---|---|
| `JDBC Driver not found` | Ensure `mysql-connector-j.jar` is in `lib/` and the `-cp` path is correct |
| `Access denied for user root` | Check `USER` and `PASSWORD` in `DBConnection.java` |
| `Unknown database farm_system` | Run `CREATE DATABASE farm_system;` in MySQL first |
| `Cannot delete landowner — FK constraint` | Delete that landowner's Workers and Fields first |

---

## 🙌 Acknowledgment

This project was created as part of the **Object-Oriented Programming (OOP)** course — *Spring 2026*.
