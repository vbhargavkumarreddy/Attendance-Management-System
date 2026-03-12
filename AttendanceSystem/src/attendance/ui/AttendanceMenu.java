package attendance.ui;

import attendance.model.AttendanceRecord;
import attendance.model.Student;
import attendance.service.AttendanceService;
import attendance.util.DateUtil;

import java.util.*;

public class AttendanceMenu {
    private final AttendanceService service = new AttendanceService();

    public void show() {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("ATTENDANCE MANAGEMENT");
            System.out.println();
            System.out.println("  1. Mark Attendance (All Students)");
            System.out.println("  2. Mark Attendance (Single Student)");
            System.out.println("  3. View Attendance by Date");
            System.out.println("  4. View Attendance by Student");
            System.out.println("  0. Back");
            System.out.println();

            String choice = ConsoleUI.prompt("Select option");
            switch (choice) {
                case "1": markAll(); break;
                case "2": markSingle(); break;
                case "3": viewByDate(); break;
                case "4": viewByStudent(); break;
                case "0": return;
                default:  ConsoleUI.printError("Invalid option.");
            }
            ConsoleUI.pause();
        }
    }

    private void markAll() {
        System.out.println();
        String date = ConsoleUI.prompt("Date (yyyy-MM-dd) [Enter for today]");
        if (date.isEmpty()) date = DateUtil.today();
        if (!DateUtil.isValidDate(date)) { ConsoleUI.printError("Invalid date."); return; }

        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) { ConsoleUI.printInfo("No students found."); return; }

        System.out.println();
        ConsoleUI.printInfo("Date: " + DateUtil.formatDisplay(date));
        ConsoleUI.printInfo("Enter P=Present, A=Absent, L=Late for each student:");
        System.out.println();

        Map<String, String> map = new LinkedHashMap<>();
        for (Student s : students) {
            String status;
            while (true) {
                String input = ConsoleUI.prompt(s.getId() + " - " + s.getName() + " [P/A/L]").toUpperCase();
                if (input.equals("P")) { status = "PRESENT"; break; }
                else if (input.equals("A")) { status = "ABSENT";  break; }
                else if (input.equals("L")) { status = "LATE";    break; }
                else ConsoleUI.printError("Enter P, A, or L.");
            }
            map.put(s.getId(), status);
        }

        service.markAllForDate(date, map);
        ConsoleUI.printSuccess("Attendance marked for " + map.size() + " student(s) on " + DateUtil.formatDisplay(date));
    }

    private void markSingle() {
        System.out.println();
        String id   = ConsoleUI.prompt("Student ID");
        String date = ConsoleUI.prompt("Date (yyyy-MM-dd) [Enter for today]");
        if (date.isEmpty()) date = DateUtil.today();

        String statusInput = ConsoleUI.prompt("Status [P=Present / A=Absent / L=Late]").toUpperCase();
        String status = statusInput.equals("P") ? "PRESENT" : statusInput.equals("A") ? "ABSENT" : "LATE";

        if (service.markAttendance(id, date, status)) {
            ConsoleUI.printSuccess("Attendance recorded.");
        } else {
            ConsoleUI.printError("Failed. Check Student ID and date.");
        }
    }

    private void viewByDate() {
        System.out.println();
        String date = ConsoleUI.prompt("Date (yyyy-MM-dd) [Enter for today]");
        if (date.isEmpty()) date = DateUtil.today();
        if (!DateUtil.isValidDate(date)) { ConsoleUI.printError("Invalid date."); return; }

        List<AttendanceRecord> records = service.getByDate(date);
        System.out.println();
        ConsoleUI.printInfo("Attendance for: " + DateUtil.formatDisplay(date));
        System.out.println();

        if (records.isEmpty()) { ConsoleUI.printInfo("No records found."); return; }

        ConsoleUI.printTableHeader("Student ID", "Status", "Marked By", "Date");
        for (AttendanceRecord r : records) {
            String statusColor = r.getStatus().equals("PRESENT") ? ConsoleUI.GREEN
                : r.getStatus().equals("ABSENT") ? ConsoleUI.RED : ConsoleUI.YELLOW;
            System.out.printf("  │ %-18s│ %s%-18s%s│ %-18s│ %-18s│%n",
                r.getStudentId(),
                statusColor, r.getStatus(), ConsoleUI.RESET,
                r.getMarkedBy(),
                DateUtil.formatDisplay(r.getDate()));
        }
        ConsoleUI.printTableFooter(4);
    }

    private void viewByStudent() {
        System.out.println();
        String id = ConsoleUI.prompt("Student ID");
        List<AttendanceRecord> records = service.getByStudent(id);
        System.out.println();

        if (records.isEmpty()) { ConsoleUI.printInfo("No records found for: " + id); return; }

        ConsoleUI.printTableHeader("Date", "Status", "Marked By", "");
        for (AttendanceRecord r : records) {
            ConsoleUI.printTableRow(DateUtil.formatDisplay(r.getDate()), r.getStatus(), r.getMarkedBy(), "");
        }
        ConsoleUI.printTableFooter(4);
        System.out.println();

        double pct = service.getPercentage(id);
        Map<String, Integer> summary = service.getSummary(id);
        ConsoleUI.printInfo("Total Classes : " + summary.get("TOTAL"));
        ConsoleUI.printInfo("Present       : " + summary.get("PRESENT"));
        ConsoleUI.printInfo("Absent        : " + summary.get("ABSENT"));
        ConsoleUI.printInfo("Late          : " + summary.get("LATE"));
        String pctColor = pct >= 75 ? ConsoleUI.GREEN : ConsoleUI.RED;
        System.out.println("  " + pctColor + ConsoleUI.BOLD + "Attendance %  : " + String.format("%.1f%%", pct) + ConsoleUI.RESET);
    }
}
