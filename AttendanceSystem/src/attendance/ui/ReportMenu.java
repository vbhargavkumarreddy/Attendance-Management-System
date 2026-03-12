package attendance.ui;

import attendance.service.AttendanceService;

import java.util.List;
import java.util.Map;

public class ReportMenu {
    private final AttendanceService service = new AttendanceService();

    public void show() {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("ATTENDANCE REPORTS");
            System.out.println();
            System.out.println("  1. Full Attendance Report (All Students)");
            System.out.println("  2. Low Attendance Alert (< 75%)");
            System.out.println("  0. Back");
            System.out.println();

            String choice = ConsoleUI.prompt("Select option");
            switch (choice) {
                case "1": fullReport(); break;
                case "2": lowAttendance(); break;
                case "0": return;
                default: ConsoleUI.printError("Invalid option.");
            }
            ConsoleUI.pause();
        }
    }

    private void fullReport() {
        System.out.println();
        ConsoleUI.printHeader("FULL ATTENDANCE REPORT");
        List<Map<String, Object>> report = service.getFullReport();

        if (report.isEmpty()) { ConsoleUI.printInfo("No data available."); return; }

        // Print header row
        System.out.printf("  │ %-8s│ %-20s│ %-16s│ %5s │ %5s │ %5s │ %5s │ %-10s│%n",
            "ID", "Name", "Course", "Total", "Pres.", "Abs.", "Late", "Attend %");
        ConsoleUI.printDivider();

        for (Map<String, Object> row : report) {
            double pct = service.getPercentage((String) row.get("id"));
            String color = pct >= 75 ? ConsoleUI.GREEN : ConsoleUI.RED;
            System.out.printf("  │ %-8s│ %-20s│ %-16s│ %5s │ %5s │ %5s │ %5s │ %s%-10s%s│%n",
                row.get("id"), row.get("name"), row.get("course"),
                row.get("total"), row.get("present"), row.get("absent"), row.get("late"),
                color, row.get("percentage"), ConsoleUI.RESET);
        }
        ConsoleUI.printDivider();
    }

    private void lowAttendance() {
        System.out.println();
        ConsoleUI.printHeader("LOW ATTENDANCE ALERT (< 75%)");
        List<Map<String, Object>> report = service.getFullReport();
        boolean found = false;

        for (Map<String, Object> row : report) {
            double pct = service.getPercentage((String) row.get("id"));
            if (pct < 75) {
                System.out.printf("  " + ConsoleUI.RED + "⚠ %-8s %-20s %-16s %s%s%n",
                    row.get("id"), row.get("name"), row.get("course"), row.get("percentage"), ConsoleUI.RESET);
                found = true;
            }
        }
        if (!found) ConsoleUI.printSuccess("All students have 75%+ attendance!");
    }
}
