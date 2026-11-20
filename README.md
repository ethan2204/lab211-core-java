# LAB211 – Core Java Lab Assignments

Console-based management applications built for the **LAB211 (Java Lab)** course at FPT University. Each lab applies object-oriented design, file-based persistence and input validation in Java.

## Labs

| Lab | Application | Main features |
|-----|-------------|---------------|
| [Lab 1](lab1-TraditionalFeastOrderManager) | Traditional Feast Order Manager | Manage customers, set menus (loaded from CSV) and feast orders; save/load data from files |
| [Lab 2](lab2-HomestayBookingManagement) | Homestay Booking Management | Manage homestays, tours and bookings with data stored in text files |
| [Lab 3](lab3-DeveloperManager) | Developer Manager | Manage developers and projects |

## Design

All labs follow the same layered structure:

- `model` – domain classes (e.g. `Customer`, `Order`, `Homestay`, `Booking`, `Dev`, `Project`)
- `business` – manager classes that hold the business logic and collections
- `tool` / `tools` – reusable input helpers (`Inputter`) and validation rules (`Acceptable`)
- `dispatcher` – `Main` class with the console menu

## Tech

Java (OOP, Collections, File I/O) · NetBeans (Ant build)

## Run

Open a lab folder in NetBeans and run the project, or run the built JAR in `dist/` where available:

```bash
java -jar dist/Lab1.jar
```
