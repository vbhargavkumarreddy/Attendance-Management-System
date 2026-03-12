package attendance.service;

import attendance.dao.UserDAO;
import attendance.model.User;

public class AuthService {
    private static AuthService instance;
    private User currentUser;
    private final UserDAO userDAO = new UserDAO();

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) instance = new AuthService();
        return instance;
    }

    public boolean login(String username, String password) {
        if (userDAO.authenticate(username, password)) {
            currentUser = userDAO.findByUsername(username);
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }
}
