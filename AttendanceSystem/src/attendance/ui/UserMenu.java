package attendance.ui;

import attendance.dao.UserDAO;
import attendance.model.User;

import java.util.List;

public class UserMenu {
    private final UserDAO userDAO = new UserDAO();

    public void show() {
        while (true) {
            ConsoleUI.clearScreen();
            ConsoleUI.printHeader("USER MANAGEMENT  [ADMIN ONLY]");
            System.out.println();
            System.out.println("  1. View All Users");
            System.out.println("  2. Add User");
            System.out.println("  3. Delete User");
            System.out.println("  0. Back");
            System.out.println();

            String choice = ConsoleUI.prompt("Select option");
            switch (choice) {
                case "1": viewAll(); break;
                case "2": addUser(); break;
                case "3": deleteUser(); break;
                case "0": return;
                default: ConsoleUI.printError("Invalid option.");
            }
            ConsoleUI.pause();
        }
    }

    private void viewAll() {
        List<User> users = userDAO.getAllUsers();
        System.out.println();
        ConsoleUI.printTableHeader("Username", "Role", "Full Name", "");
        for (User u : users) {
            ConsoleUI.printTableRow(u.getUsername(), u.getRole(), u.getFullName(), "");
        }
        ConsoleUI.printTableFooter(4);
    }

    private void addUser() {
        System.out.println();
        String username = ConsoleUI.prompt("Username");
        String password = ConsoleUI.promptPassword("Password");
        String role     = ConsoleUI.prompt("Role [ADMIN/TEACHER]").toUpperCase();
        String fullName = ConsoleUI.prompt("Full Name");

        if (!role.equals("ADMIN") && !role.equals("TEACHER")) {
            ConsoleUI.printError("Invalid role. Use ADMIN or TEACHER.");
            return;
        }

        if (userDAO.addUser(new User(username, password, role, fullName))) {
            ConsoleUI.printSuccess("User added: " + username);
        } else {
            ConsoleUI.printError("Username already exists.");
        }
    }

    private void deleteUser() {
        System.out.println();
        String username = ConsoleUI.prompt("Username to delete");
        String confirm  = ConsoleUI.prompt("Are you sure? (yes/no)");
        if ("yes".equalsIgnoreCase(confirm)) {
            if (userDAO.deleteUser(username)) {
                ConsoleUI.printSuccess("User deleted.");
            } else {
                ConsoleUI.printError("User not found.");
            }
        }
    }
}
