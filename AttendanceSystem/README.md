# Attendance Management System
## Java Console Application | v1.0

---

## Project Structure
```
AttendanceSystem/
├── src/
│   └── attendance/
│       ├── Main.java                    ← Entry point
│       ├── model/
│       │   ├── User.java                ← User data model
│       │   ├── Student.java             ← Student data model
│       │   └── AttendanceRecord.java    ← Attendance record model
│       ├── dao/
│       │   ├── UserDAO.java             ← User file operations
│       │   ├── StudentDAO.java          ← Student file operations
│       │   └── AttendanceDAO.java       ← Attendance file operations
│       ├── service/
│       │   ├── AuthService.java         ← Login & session management
│       │   └── AttendanceService.java   ← Business logic
│       ├── ui/
│       │   ├── ConsoleUI.java           ← UI helpers & colors
│       │   ├── LoginScreen.java         ← Login interface
│       │   ├── MainMenu.java            ← Dashboard
│       │   ├── StudentMenu.java         ← Student management screens
│       │   ├── AttendanceMenu.java      ← Attendance entry screens
│       │   ├── ReportMenu.java          ← Reports & analytics
│       │   └── UserMenu.java            ← User management (Admin)
│       └── util/
│           ├── FileUtil.java            ← CSV read/write helper
│           └── DateUtil.java            ← Date formatting/validation
├── data/                                ← Auto-generated CSV data files
│   ├── users.csv
│   ├── students.csv
│   └── attendance.csv
├── run.sh                               ← Linux/Mac build & run
├── run.bat                              ← Windows build & run
└── README.md
```

---

## Step-by-Step Setup Guide

### STEP 1: Install Java JDK
- Download JDK 11 or higher from: https://adoptium.net
- Install and follow the installer instructions
- Verify installation:
  ```
  java -version
  javac -version
  ```

### STEP 2: Extract Project Files
- Extract the `AttendanceSystem` folder to any location, e.g., `C:\Projects\AttendanceSystem`

### STEP 3: Open Terminal / Command Prompt
- **Windows**: Press Win+R → type `cmd` → navigate to project folder
  ```
  cd C:\Projects\AttendanceSystem
  ```
- **Mac/Linux**: Open Terminal → navigate to project folder
  ```
  cd ~/Projects/AttendanceSystem
  ```

### STEP 4: Compile the Project

**Windows:**
```cmd
mkdir bin
mkdir data
for /r src %f in (*.java) do javac -d bin "%f"
```

**Mac/Linux:**
```bash
mkdir -p bin data
find src -name "*.java" | xargs javac -d bin
```

OR simply run the provided scripts:
- **Windows**: Double-click `run.bat`
- **Mac/Linux**: `chmod +x run.sh && ./run.sh`

### STEP 5: Run the Application

**Windows:**
```cmd
cd bin
java attendance.Main
```

**Mac/Linux:**
```bash
cd bin && java attendance.Main
```

### STEP 6: Login with Default Credentials

| Role    | Username | Password |
|---------|----------|----------|
| Admin   | admin    | admin123 |
| Teacher | teacher1 | pass123  |

> **Note:**
> * If you launch the program from within an IDE (Run/Debug button) you may not have a real console attached. In that situation you will either be unable to type anything or see no characters when you enter the password. The application now echoes `*` characters for every keystroke when the console is unavailable, but the safest approach is to start the program from a proper terminal window (see Step 5) so that `System.console()` is non-null.
> * When the `System.console()` object is available the password input is hidden for security (nothing is echoed); you should simply type blindly and press **ENTER**. This is normal behaviour.
---

## Features

### Authentication
- Secure login with 3-attempt lockout
- Role-based access: ADMIN and TEACHER roles
- Session management (login/logout)

### Student Management
- Add, update, delete, search students
- Fields: ID, Name, Course, Email
- Bulk student listing

### Attendance Entry
- Mark attendance by date for all students at once
- Mark individual student attendance
- Status options: PRESENT / ABSENT / LATE
- Edit already-entered records for a date

### Reports & Analytics
- Full attendance report with percentage per student
- Color-coded attendance (Green ≥75%, Red <75%)
- Low attendance alert (highlights students below 75%)
- Per-student attendance history

### User Management (Admin Only)
- Add new users (Admin/Teacher roles)
- Delete existing users
- View all system users

---

## Data Storage
All data is stored in CSV files inside the `data/` folder:
- `data/users.csv` — System users
- `data/students.csv` — Student records
- `data/attendance.csv` — Attendance records

These files are created automatically on first run.

---

## Sample Workflow

1. Login as `admin` / `admin123`
2. Go to **Student Management** → Add your students
3. Go to **Attendance Entry** → Mark Attendance (All Students)
4. Enter date or press Enter for today
5. Mark P/A/L for each student
6. Go to **Reports** → View Full Report with percentages

---

## Requirements
- Java JDK 11 or higher
- No external libraries needed (pure Java SE)
- Works on Windows, macOS, Linux
