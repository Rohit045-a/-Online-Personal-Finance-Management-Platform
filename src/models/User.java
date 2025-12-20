package models;

import java.time.LocalDateTime;

public abstract class User {
    protected int userId;
    protected String username;
    protected String email;
    protected String password;
    protected UserRole role;
    protected LocalDateTime createdDate;

    public enum UserRole {
        ADMIN, ADVISOR, USER
    }

    public User(int userId, String username, String email, String password, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = LocalDateTime.now();
    }

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = LocalDateTime.now();
    }

    public abstract void displayDashboard();

    public abstract boolean performOperation(String operationType);

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public LocalDateTime getCreatedDate() { return createdDate; }
}
