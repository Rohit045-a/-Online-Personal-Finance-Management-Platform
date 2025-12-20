package models;

import java.time.LocalDateTime;

public class Expense implements Comparable<Expense> {
    private int expenseId;
    private int userId;
    private String category;
    private double amount;
    private String description;
    private LocalDateTime expenseDate;

    public Expense(int userId, String category, double amount, String description) {
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.expenseDate = LocalDateTime.now();
    }

    public Expense(int expenseId, int userId, String category, double amount, String description, LocalDateTime expenseDate) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    @Override
    public int compareTo(Expense other) {
        return Double.compare(this.amount, other.amount);
    }

    public int getExpenseId() { return expenseId; }
    public int getUserId() { return userId; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getExpenseDate() { return expenseDate; }

    @Override
    public String toString() {
        return String.format("[%s] $%.2f - %s", category, amount, description);
    }
}
