@echo off
REM =====================================================
REM  Attendance Management System - Build & Run Script
REM =====================================================

echo.
echo   Attendance Management System - Build
echo   =====================================
echo.

REM Create directories
if not exist bin mkdir bin
if not exist data mkdir data

REM Compile all Java files
echo   [1/2] Compiling source files...
for /r src %%f in (*.java) do (
    javac -d bin "%%f"
)

if %errorlevel% equ 0 (
    echo   Compilation successful!
    echo.
    echo   [2/2] Launching application...
    echo.
    cd bin
    java attendance.Main
) else (
    echo   Compilation failed. Ensure JDK 11+ is installed.
    echo   Download from: https://adoptium.net
)
pause
