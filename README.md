# CodeAlpha - Student Grade Tracker

**Internship Domain:** Java Programming  
**Project Name:** `CodeAlpha_StudentGradeTracker`  
**Language:** Java (JDK 8+)  
**Data Structure:** `ArrayList`  
**Application Type:** Console / Command-Line Interface (CLI)

---

## 📌 Project Overview

The **Student Grade Tracker** is a clean, modular Java console application built to help teachers and educators manage student grades and track class performance. 

It allows users to enter student details, stores data dynamically in memory using `ArrayList`, enforces strict input validation (0–100 score range), and calculates statistical metrics including **class average**, **highest score**, **lowest score**, and **letter grade distributions**.

---

## 🚀 Features

- **Dynamic Data Storage:** Uses Java `ArrayList` to store and manage multiple student records in memory without fixed array size limitations.
- **Robust Input Validation:**
  - Validates marks to ensure they fall strictly between **0.0 and 100.0**.
  - Prevents blank or whitespace-only student names.
  - Safely handles non-numeric inputs without crashing.
- **Statistical Computations:**
  - Computes exact **Class Average** marks.
  - Identifies **Highest Scorer** and **Lowest Scorer** along with student names.
  - Calculates **Overall Pass Rate** percentage.
- **Letter Grade Classification:**
  - `A+` (90 – 100)
  - `A`  (80 – 89.99)
  - `B`  (70 – 79.99)
  - `C`  (60 – 69.99)
  - `D`  (50 – 59.99)
  - `F`  (Below 50)
- **Formatted Tabular Reports:** Displays student lists and summary cards cleanly in the terminal.
- **Reset Option:** Easily clear recorded grades to start a new batch.

---

## 🏗️ Architecture & OOP Concepts

The project follows clean Object-Oriented Programming (OOP) principles:

```
CodeAlpha_StudentGradeTracker/
├── Student.java       # Model class (Encapsulation: private fields, getters/setters, letter grade logic)
├── GradeTracker.java  # Service class (Business logic: ArrayList management, statistics & reports)
├── Main.java          # Presentation layer (Interactive CLI menu and input validation)
└── README.md          # Project documentation
```

### OOP Principles Applied:
1. **Encapsulation:** The `Student` class encapsulates `name` and `marks` as `private` attributes, accessible only via public getters and setters.
2. **Separation of Concerns:**
   - `Student`: Represents data entity.
   - `GradeTracker`: Handles statistical algorithms and collections.
   - `Main`: Manages user interactions and console I/O.
3. **No Heavy Frameworks:** Pure Core Java without Spring Boot, databases, or unnecessary third-party dependencies.

---

## 📋 Prerequisites

- **Java Development Kit (JDK):** Version 8 or higher (Java 11 / 17 / 21 recommended)
- Any Terminal / Command Prompt or Java IDE (IntelliJ IDEA, Eclipse, VS Code, NetBeans)

Verify Java installation:
```bash
java -version
javac -version
```

---

## 🛠️ How to Compile and Run

### 1. Using Command Line / Terminal

1. Open your terminal or command prompt inside the project folder:
   ```bash
   cd CodeAlpha_StudentGradeTracker
   ```

2. Compile all Java source files:
   ```bash
   javac Student.java GradeTracker.java Main.java
   ```

3. Run the application:
   ```bash
   java Main
   ```

---

## 🖥️ Sample Run Demonstration

### Main Menu
```
========================================================
         CodeAlpha - Student Grade Tracker              
            Java Programming Internship                 
========================================================

----------------- MAIN MENU -----------------
 1. Add Student Grade(s)
 2. View All Students and Marks
 3. View Summary Report (Average, High, Low)
 4. Clear All Student Records
 5. Exit Application
---------------------------------------------
Enter your choice (1-5): 1
```

### Adding Records
```
--- Add Student Grade ---
How many students would you like to add? 3

Student #1:
  Enter Student Name: Alice Smith
  Enter Marks (0 - 100): 95.5
  [✓] Successfully added Alice Smith with 95.50 marks (Grade A+).

Student #2:
  Enter Student Name: Bob Johnson
  Enter Marks (0 - 100): 78.0
  [✓] Successfully added Bob Johnson with 78.00 marks (Grade B).

Student #3:
  Enter Student Name: Charlie Brown
  Enter Marks (0 - 100): 48.0
  [✓] Successfully added Charlie Brown with 48.00 marks (Grade F).

[✓] All 3 student record(s) added successfully!
```

### Student List (Option 2)
```
========================================================
                   STUDENT RECORDS LIST                 
========================================================
No.   | Student Name         | Marks      | Grade 
--------------------------------------------------------
1     | Alice Smith          |      95.50 | A+    
2     | Bob Johnson          |      78.00 | B     
3     | Charlie Brown        |      48.00 | F     
========================================================
Total Students: 3
```

### Statistical Summary Report (Option 3)
```
========================================================
               PERFORMANCE SUMMARY REPORT               
========================================================
 Total Students Recorded : 3
 Class Average Marks     : 73.83 / 100
 Highest Marks Scored    : 95.50 (By Alice Smith)
 Lowest Marks Scored     : 48.00 (By Charlie Brown)
 Overall Pass Rate       : 66.67% (2 passed, 1 failed)
--------------------------------------------------------
 Grade Distribution Breakdown:
   - Grade A+/A (80-100) : 1 student(s)
   - Grade B    (70-79)  : 1 student(s)
   - Grade C    (60-69)  : 0 student(s)
   - Grade D    (50-59)  : 0 student(s)
   - Grade F    (< 50)   : 1 student(s)
========================================================
```

---

## 👨‍💻 Internship Submission Details

- **Company:** CodeAlpha
- **Track:** Java Programming Internship
- **Task:** Task 1 - Student Grade Tracker
