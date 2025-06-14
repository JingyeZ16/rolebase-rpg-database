RoleBase: Relational RPG Management System
A full-stack database-backed application simulating an online role-playing game backend. This project demonstrates end-to-end data engineering skills: from ER modeling and SQL schema design to Java-based CRUD operations and JSP/Servlet-powered web interaction.

🚀 Tech Stack
Java · JDBC · MySQL · SQL · JSP/Servlets

ER Modeling · DAO Pattern · MVC Architecture

📌 Key Features
ER Modeling: Designed and normalized the database to third normal form (3NF) with clearly defined PK-FK relationships and constraints.

Physical Schema: Created SQL DDL scripts for all tables, using AUTO_INCREMENT, FOREIGN KEY, and UNIQUE constraints.

Data Access Layer: Built DAO classes for all tables supporting 100+ CRUD operations with JDBC and parameterized queries to prevent SQL injection.

Web Interface: Implemented JSP pages and Servlets to support dynamic querying, partial filtering, detail views, and record updates across 3+ joined tables.

Demo & Testing: Populated each table with 10+ sample records, and built a Driver.java class for schema setup and data seeding.

🧪 Sample Use Cases
Filter characters by name or class

View detailed player profiles (including inventory and stats)

Edit in-game attributes directly from web UI
