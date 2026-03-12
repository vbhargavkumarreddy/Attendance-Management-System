package attendance.ui;

import attendance.service.AuthService;

public class MainMenu {
    private final AuthService auth = AuthService.getInstance();
    private final StudentMenu studentMenu = new StudentMenu();
    private final AttendanceMenu attendanceMenu = new AttendanceMenu();
    private final ReportMenu reportMenu = new ReportMenu();
    private final UserMenu userMenu = new UserMenu();

    public void show() {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printBanner();
            ConsoleUI.printHeader("MAIN MENU  |  " + auth.getCurrentUser().getFullName()
                + "  [" + auth.getCurrentUser().getRole() + "]");
            System.out.println();
            System.out.println("  1. Student Management");
            System.out.println("  2. Attendance Entry");
            System.out.println("  3. Reports & Analytics");
            if (auth.isAdmin()) {
                System.out.println("  4. User Management");
            }
            System.out.println("  0. Logout");
            System.out.println();

            String choice = ConsoleUI.prompt("Select option");
            switch (choice) {
                case "1": studentMenu.show(); break;
                case "2": attendanceMenu.show(); break;
                case "3": reportMenu.show(); break;
                case "4":
                    if (auth.isAdmin()) userMenu.show();
                    else ConsoleUI.printError("Admin access required.");
                    break;
                case "0":
                    auth.logout();
                    ConsoleUI.printInfo("Logged out. Goodbye!");
                    return;
                default:
                    ConsoleUI.printError("Invalid option.");
                    ConsoleUI.pause();
            }
        }
    }
}
