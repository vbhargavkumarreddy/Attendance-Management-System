package attendance;

import attendance.dao.StudentDAO;
import attendance.dao.UserDAO;
import attendance.ui.ConsoleUI;
import attendance.ui.LoginScreen;
import attendance.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        // Initialize default data on first run
        new UserDAO().initializeDefaults();
        new StudentDAO().initializeSampleData();

        LoginScreen login = new LoginScreen();
        MainMenu mainMenu = new MainMenu();

        while (true) {
            boolean loggedIn = login.show();
            if (!loggedIn) break;
            mainMenu.show();

            // After logout, ask to login again or exit
            ConsoleUI.clearScreen();
            ConsoleUI.printBanner();
            String choice = ConsoleUI.prompt("Login again? (yes/no)");
            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println(ConsoleUI.CYAN + ConsoleUI.BOLD
                    + "\n  Thank you for using Attendance Management System!\n" + ConsoleUI.RESET);
                break;
            }
        }
    }
}
