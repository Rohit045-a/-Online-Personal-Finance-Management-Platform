package services;

import models.*;
import dao.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class FinanceService {
    private UserDAO userDAO;
    private ExpenseDAO expenseDAO;
    private BudgetDAO budgetDAO;

    public FinanceService() {
        this.userDAO = new UserDAO();
        this.expenseDAO = new ExpenseDAO();
        this.budgetDAO = new BudgetDAO();
    }

    public void registerUser(User user) throws SQLException {
        userDAO.addUser(user);
    }

    public User loginUser(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public void addExpense(int userId, String category, double amount, String description) throws SQLException {
        Expense expense = new Expense(userId, category, amount, description);
        expenseDAO.addExpense(expense);
    }

    public List<Expense> getUserExpenses(int userId) throws SQLException {
        return expenseDAO.getUserExpenses(userId);
    }

    public double calculateTotalExpenses(int userId) throws SQLException {
        List<Expense> expenses = expenseDAO.getUserExpenses(userId);
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public Map<String, Double> getCategoryExpenses(int userId) throws SQLException {
        List<Expense> expenses = expenseDAO.getUserExpenses(userId);
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public void setBudget(int userId, String category, double amount) throws SQLException {
        Budget budget = new Budget(category, amount);
        budgetDAO.addBudget(userId, budget);
    }

    public List<Budget> getUserBudgets(int userId) throws SQLException {
        return budgetDAO.getUserBudgets(userId);
    }

    public Map<String, Object> getFinancialOverview(int userId) throws SQLException {
        Map<String, Object> overview = new HashMap<>();
        
        double totalExpenses = calculateTotalExpenses(userId);
        Map<String, Double> categoryExpenses = getCategoryExpenses(userId);
        List<Budget> budgets = getUserBudgets(userId);
        
        overview.put("totalExpenses", totalExpenses);
        overview.put("categoryExpenses", categoryExpenses);
        overview.put("budgets", budgets);
        overview.put("expenseCount", expenseDAO.getUserExpenses(userId).size());
        
        return overview;
    }

    public List<String> generateFinancialAdvice(int userId) throws SQLException {
        List<String> advice = new ArrayList<>();
        Map<String, Object> overview = getFinancialOverview(userId);
        
        double totalExpenses = (double) overview.get("totalExpenses");
        @SuppressWarnings("unchecked")
        Map<String, Double> categoryExpenses = (Map<String, Double>) overview.get("categoryExpenses");
        
        if (totalExpenses > 5000) {
            advice.add("Your expenses are quite high. Consider reducing discretionary spending.");
        }
        
        if (categoryExpenses.containsKey("Entertainment") && categoryExpenses.get("Entertainment") > 500) {
            advice.add("Entertainment expenses are significant. Consider setting stricter limits.");
        }
        
        if (categoryExpenses.containsKey("Food") && categoryExpenses.get("Food") > 800) {
            advice.add("Food expenses are above average. Consider meal planning or cooking at home.");
        }
        
        advice.add("Keep tracking your expenses regularly for better financial health.");
        
        return advice;
    }
}
