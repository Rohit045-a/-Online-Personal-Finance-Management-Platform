package dao;

import database.DatabaseConfig;
import java.sql.*;
import java.util.*;
import models.*;

public class UserDAO {
    private static final String INSERT_USER = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET email = ?, password = ? WHERE user_id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";

    public User addUser(User user) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getRole().toString());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                }
            }
        }
        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_USERNAME)) {
            
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createUserFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER)) {
            
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getUserId());
            
            pstmt.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_USER)) {
            
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");

        switch (role) {
            case "ADMIN":
                return new Admin(userId, username, email, password);
            case "ADVISOR":
                return new FinancialAdvisor(userId, username, email, password);
            case "USER":
                return new RegularUser(userId, username, email, password);
            default:
                return null;
        }
    }
}
