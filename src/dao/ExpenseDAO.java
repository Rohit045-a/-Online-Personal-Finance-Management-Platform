package dao;

import models.Expense;
import database.DatabaseConfig;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ExpenseDAO {
    private static final String INSERT_EXPENSE = "INSERT INTO expenses (user_id, category, amount, description) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER_EXPENSES = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
    private static final String SELECT_EXPENSE_BY_ID = "SELECT * FROM expenses WHERE expense_id = ?";
    private static final String DELETE_EXPENSE = "DELETE FROM expenses WHERE expense_id = ?";

    public void addExpense(Expense expense) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_EXPENSE)) {
            
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getCategory());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, expense.getDescription());
            
            pstmt.executeUpdate();
        }
    }

    public List<Expense> getUserExpenses(int userId) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_EXPENSES)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    expenses.add(createExpenseFromResultSet(rs));
                }
            }
        }
        return expenses;
    }

    public void deleteExpense(int expenseId) throws SQLException {
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_EXPENSE)) {
            
            pstmt.setInt(1, expenseId);
            pstmt.executeUpdate();
        }
    }

    private Expense createExpenseFromResultSet(ResultSet rs) throws SQLException {
        int expenseId = rs.getInt("expense_id");
        int userId = rs.getInt("user_id");
        String category = rs.getString("category");
        double amount = rs.getDouble("amount");
        String description = rs.getString("description");
        Timestamp timestamp = rs.getTimestamp("expense_date");
        LocalDateTime expenseDate = timestamp != null ? timestamp.toLocalDateTime() : LocalDateTime.now();

        return new Expense(expenseId, userId, category, amount, description, expenseDate);
    }
}
