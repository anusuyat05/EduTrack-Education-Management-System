# EduTrack – Education Management System
EduTrack is a Java console-based application designed to help private educational academies efficiently manage students, trainers, courses, batches, topics, enrollments, and promotional free course offers. The system uses role-based access control for Admins and Trainers.

---

## Key Functional Modules
- **Student Management**
    - Store student info (name, gender, DOB, email, phone)
    - Update and delete records with validations

- **Trainer Management**
    - Admin can add trainers and assign admin access
    - Trainers can only manage their content

- **Course & Batch Management**
    - Manage both paid and free courses
    - Assign batches with class modes and dates

- **Free Course Offer Linking**
    - Admins can map free courses to paid ones
    - Eligibility checks before enrollment

- **Enrollments**
    - Prevent duplicate enrollments
    - Only allow free course enrollment if paid course is enrolled

---

## Tech Stack

- **Language:** Java (JDK 17 or above recommended)
- **IDE:** IntelliJ IDEA
- **Database:** postgreSQL (via JDBC)
- **Architecture:** DAO Layer, Service Layer, Utility Layer

---

## Console Run - Home
<img width="230" height="84" alt="image" src="https://github.com/user-attachments/assets/91f82d6f-d68e-45b0-aee4-061510ce73e5" />

---

## Admin Login
<img width="212" height="197" alt="image" src="https://github.com/user-attachments/assets/d23d18e6-a14a-409f-b443-ee6159cd6759" />

---

## Trainer Login
<img width="206" height="167" alt="image" src="https://github.com/user-attachments/assets/35cc4877-f9a8-4a97-9e5a-e19bef13cb06" />




