#!/bin/bash
# =====================================================
#  Attendance Management System - Build & Run Script
# =====================================================

echo ""
echo "  ╔════════════════════════════════════════╗"
echo "  ║  Attendance Management System - Build  ║"
echo "  ╚════════════════════════════════════════╝"
echo ""

# Create directories
mkdir -p bin data

# Compile
echo "  [1/2] Compiling source files..."
find src -name "*.java" | xargs javac -d bin

if [ $? -eq 0 ]; then
    echo "  ✔ Compilation successful!"
    echo ""
    echo "  [2/2] Launching application..."
    echo ""
    cd bin && java attendance.Main
else
    echo "  ✘ Compilation failed. Please ensure JDK 11+ is installed."
    echo "      Download: https://adoptium.net"
fi
