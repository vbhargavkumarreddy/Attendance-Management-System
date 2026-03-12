package attendance.model;

public class User {
    private String username;
    private String password;
    private String role; // "ADMIN" or "TEACHER"
    private String fullName;

    public User(String username, String password, String role, String fullName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getFullName() { return fullName; }

    @Override
    public String toString() {
        return username + "," + password + "," + role + "," + fullName;
    }
}
