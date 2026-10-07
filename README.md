# Student Database Query System

A Java Swing application for querying and filtering student records from a MySQL database. The project is designed for learning database interaction, SQL execution, and GUI-based result display in Java.

## Overview

This application connects to a local MySQL database named `studentdb` and lets users run `SELECT` queries through a simple graphical interface. Query results are displayed in a table, and users can apply a text filter to quickly search through the records.

## Features

- Execute SQL `SELECT` queries from a Swing interface
- View results in a table format
- Apply quick text-based filtering to result rows
- Clear query and result display anytime
- Connect directly to a local MySQL database using JDBC
- Includes sample student data and example SQL queries

## Tech Stack

- Java
- Swing (GUI)
- JDBC
- MySQL
- MySQL Connector/J

## Project Structure

```text
StudentDatabaseProject/
├── database/
│   └── studentdb.sql
├── lib/
│   └── mysql-connector-java-8.0.30.jar
├── src/
│   └── StudentDatabaseApp.java
├── queries.txt
├── Results/
├── out/
├── README.md
└── .gitignore
```

## Prerequisites

Before running the project, make sure you have:

- Java JDK installed
- MySQL Server installed and running
- MySQL Connector/J JAR available in the `lib` folder
- A MySQL user with access to the `studentdb` database

## Database Setup

1. Open MySQL and create the database by running the SQL script in `database/studentdb.sql`.
2. The script will:
   - create the `studentdb` database
   - create the `student` table
   - insert sample student records

Example:

```sql
CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;
```

## Configuration

The application is configured with the following default MySQL settings in `src/StudentDatabaseApp.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "1234";
```

If your MySQL username/password is different, update these values before running the project.

## Running the Project

### 1. Compile the Java file

Open a terminal in the project root and run:

```bash
javac -cp "lib/mysql-connector-java-8.0.30.jar" -d out src/StudentDatabaseApp.java
```

### 2. Run the application

On Windows:

```bash
java -cp "out;lib/mysql-connector-java-8.0.30.jar" StudentDatabaseApp
```

On macOS/Linux:

```bash
java -cp "out:lib/mysql-connector-java-8.0.30.jar" StudentDatabaseApp
```

## Using the App

1. Enter an SQL query in the text area.
2. Click `Execute Query`.
3. Results appear in the table below.
4. Use the filter box to search through the displayed rows.

> Only `SELECT` queries are allowed in this application for safety and display purposes.

## Example Queries

The project includes sample SQL statements in `queries.txt`, including:

```sql
SELECT * FROM student;
SELECT * FROM student WHERE marks >= 90;
SELECT * FROM student WHERE course = 'CSE AIML';
SELECT * FROM student WHERE semester = 3;
SELECT * FROM student WHERE name LIKE '%Aryan%';
```

## Notes

- This project is best suited for educational purposes and beginner SQL/JDBC practice.
- The app is a local database query interface and is not intended for production-level database access.
- If MySQL is not running or the credentials do not match, the application will show a connection error.

## License

This project is provided for academic and learning use.
