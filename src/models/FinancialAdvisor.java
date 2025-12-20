package models;

import java.util.*;

public class FinancialAdvisor extends User {
    private List<Integer> assignedUsers;
    private Map<Integer, List<String>> adviceHistory;

    public FinancialAdvisor(int userId, String username, String email, String password) {
        super(userId, username, email, password, UserRole.ADVISOR);
        this.assignedUsers = new ArrayList<>();
        this.adviceHistory = new HashMap<>();
    }

    public FinancialAdvisor(String username, String email, String password) {
        super(username, email, password, UserRole.ADVISOR);
        this.assignedUsers = new ArrayList<>();
        this.adviceHistory = new HashMap<>();
    }

    @Override
    public void displayDashboard() {
        System.out.println("=== FINANCIAL ADVISOR DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Assigned Users: " + assignedUsers.size());
    }

    @Override
    public boolean performOperation(String operationType) {
        switch (operationType.toUpperCase()) {
            case "PROVIDE_ADVICE":
            case "VIEW_USER_PROGRESS":
            case "INTERACT_WITH_USERS":
                return true;
            default:
                return false;
        }
    }

    public void assignUser(int userId) {
        assignedUsers.add(userId);
        adviceHistory.put(userId, new ArrayList<>());
    }

    public void addAdvice(int userId, String advice) {
        if (adviceHistory.containsKey(userId)) {
            adviceHistory.get(userId).add(advice);
        }
    }

    public List<String> getAdviceForUser(int userId) {
        return adviceHistory.getOrDefault(userId, new ArrayList<>());
    }

    public List<Integer> getAssignedUsers() {
        return new ArrayList<>(assignedUsers);
    }
}
