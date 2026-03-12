package attendance.ui;

import attendance.service.AuthService;

public class LoginScreen {
    private final AuthService auth = AuthService.getInstance();

    public boolean show() {
        ConsoleUI.clearScreen();
        ConsoleUI.printBanner();
        ConsoleUI.printHeader("LOGIN");
        System.out.println();

        for (int attempts = 1; attempts <= 3; attempts++) {
            String username = ConsoleUI.prompt("Username");
            String password = ConsoleUI.promptPassword("Password");

            if (auth.login(username, password)) {
                ConsoleUI.printSuccess("Welcome, " + auth.getCurrentUser().getFullName() + "! ["
                    + auth.getCurrentUser().getRole() + "]");
                ConsoleUI.pause();
                return true;
            }
            ConsoleUI.printError("Invalid credentials. Attempt " + attempts + "/3");
            System.out.println();
        }
        ConsoleUI.printError("Too many failed attempts. Exiting.");
        return false;
    }
}
