package models;

import java.util.*;

public class RegularUser extends User {
    private double totalIncome;
    private List<Expense> expenses;
    private Map<String, Budget> budgets;

    public RegularUser(int userId, String username, String email, String password) {
        super(userId, username, email, password, UserRole.USER);
        this.totalIncome = 0.0;
        this.expenses = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    public RegularUser(String username, String email, String password) {
        super(username, email, password, UserRole.USER);
        this.totalIncome = 0.0;
        this.expenses = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    @Override
    public void displayDashboard() {
        System.out.println("=== USER DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Total Income: $" + totalIncome);
        System.out.println("Total Expenses: $" + calculateTotalExpenses());
    }

    @Override
    public boolean performOperation(String operationType) {
        switch (operationType.toUpperCase()) {
            case "TRACK_EXPENSES":
            case "SET_BUDGET":
            case "REQUEST_ADVICE":
                return true;
            default:
                return false;
        }
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void setBudget(String category, double amount) {
        budgets.put(category, new Budget(category, amount));
    }

    public double calculateTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double calculateCategoryExpenses(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equals(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }

    public Map<String, Budget> getBudgets() {
        return new HashMap<>(budgets);
    }

    public void setTotalIncome(double income) {
        this.totalIncome = income;
    }

    public double getTotalIncome() {
        return totalIncome;
    }
}
