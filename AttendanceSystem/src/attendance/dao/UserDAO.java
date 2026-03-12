package attendance.dao;

import attendance.model.User;
import attendance.util.FileUtil;

import java.util.*;

public class UserDAO {
    private static final String FILE = "data/users.csv";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE)) {
            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                users.add(new User(parts[0], parts[1], parts[2], parts[3]));
            }
        }
        return users;
    }

    public User findByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        User u = findByUsername(username);
        return u != null && u.getPassword().equals(password);
    }

    public boolean addUser(User user) {
        if (findByUsername(user.getUsername()) != null) return false;
        FileUtil.appendLine(FILE, user.toString());
        return true;
    }

    public boolean deleteUser(String username) {
        List<User> users = getAllUsers();
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (User u : users) {
            if (!u.getUsername().equalsIgnoreCase(username)) {
                lines.add(u.toString());
            } else {
                found = true;
            }
        }
        if (found) FileUtil.writeLines(FILE, lines);
        return found;
    }

    public void initializeDefaults() {
        if (getAllUsers().isEmpty()) {
            addUser(new User("admin", "admin123", "ADMIN", "System Administrator"));
            addUser(new User("teacher1", "pass123", "TEACHER", "John Smith"));
        }
    }
}
