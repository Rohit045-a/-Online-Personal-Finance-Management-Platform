package models;

import java.time.LocalDateTime;

public class Budget {
    private int budgetId;
    private int userId;
    private String category;
    private double amount;
    private LocalDateTime createdDate;

    public Budget(String category, double amount) {
        this.category = category;
        this.amount = amount;
        this.createdDate = LocalDateTime.now();
    }

    public Budget(int budgetId, int userId, String category, double amount, LocalDateTime createdDate) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    public int getBudgetId() { return budgetId; }
    public int getUserId() { return userId; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public LocalDateTime getCreatedDate() { return createdDate; }
}
