package dao;

import models.Budget;
import database.DatabaseConfig;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class BudgetDAO {
    private static final String INSERT_BUDGET = "INSERT INTO budgets (user_id, category, amount) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BUDGETS = "SELECT * FROM budgets WHERE user_id = ?";
    private static final String UPDATE_BUDGET = "UPDATE budgets SET amount = ? WHERE budget_id = ?";
    private static final String DELETE_BUDGET = "DELETE FROM budgets WHERE budget_id = ?";

    public void addBudget(int userId, Budget budget) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_BUDGET)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, budget.getCategory());
            pstmt.setDouble(3, budget.getAmount());
            
            pstmt.executeUpdate();
        }
    }

    public List<Budget> getUserBudgets(int userId) throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BUDGETS)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    budgets.add(createBudgetFromResultSet(rs));
                }
            }
        }
        return budgets;
    }

    public void updateBudget(int budgetId, double newAmount) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_BUDGET)) {
            
            pstmt.setDouble(1, newAmount);
            pstmt.setInt(2, budgetId);
            
            pstmt.executeUpdate();
        }
    }

    public void deleteBudget(int budgetId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BUDGET)) {
            
            pstmt.setInt(1, budgetId);
            pstmt.executeUpdate();
        }
    }

    private Budget createBudgetFromResultSet(ResultSet rs) throws SQLException {
        int budgetId = rs.getInt("budget_id");
        int userId = rs.getInt("user_id");
        String category = rs.getString("category");
        double amount = rs.getDouble("amount");
        Timestamp timestamp = rs.getTimestamp("created_date");
        LocalDateTime createdDate = timestamp != null ? timestamp.toLocalDateTime() : LocalDateTime.now();

        return new Budget(budgetId, userId, category, amount, createdDate);
    }
}
