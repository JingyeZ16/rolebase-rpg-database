# 🎮 RoleBase: Relational RPG Management System

A full-stack database-backed application simulating an online role-playing game backend. This project demonstrates end-to-end data engineering skills, from ER modeling and SQL schema design to Java-based CRUD operations and JSP/Servlet-powered web interaction.

---

## 🚀 Tech Stack

- **Languages & Tools:** Java, JDBC, MySQL, SQL  
- **Web Framework:** JSP/Servlets  
- **Design Patterns:** ER Modeling, DAO Pattern, MVC Architecture

---

## 📌 Key Features

- **🧩 ER Modeling:**  
  Designed and normalized the database to third normal form (3NF) with clearly defined primary and foreign key constraints.

- **🛠️ Physical Schema:**  
  Wrote SQL DDL scripts with `AUTO_INCREMENT`, `FOREIGN KEY`, and `UNIQUE` constraints to enforce data integrity.

- **🗂️ Data Access Layer (DAO):**  
  Developed DAO classes for all major tables, supporting 100+ CRUD operations via JDBC and securing queries with parameterized statements.

- **🌐 Web Interface:**  
  Implemented dynamic JSP pages and Java Servlets to enable real-time querying, partial filtering, detailed views, and record updates across multi-table joins.

- **🧪 Demo & Testing:**  
  Populated all tables with 10+ synthetic records and built a `Driver.java` class to create and seed the schema programmatically.

---

## 🧪 Sample Use Cases

- 🔍 Filter characters by **name** or **class**  
- 📋 View detailed **player profiles** including inventory, stats, and linked entities  
- ✏️ Edit in-game attributes directly from the web interface  

---

## 📁 Project Structure

```text
├── sql/                # SQL scripts (DDL, inserts)
├── src/
│   ├── model/          # Java model classes (POJOs)
│   ├── dao/            # DAO classes with CRUD logic
│   ├── servlet/        # Java Servlets (controllers)
│   ├── jsp/            # JSP files (frontend)
│   └── Driver.java     # Sets up and seeds the DB


