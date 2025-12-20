package models;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<String> managedSecurityPolicies;
    private List<String> systemLogs;

    public Admin(int userId, String username, String email, String password) {
        super(userId, username, email, password, UserRole.ADMIN);
        this.managedSecurityPolicies = new ArrayList<>();
        this.systemLogs = new ArrayList<>();
    }

    public Admin(String username, String email, String password) {
        super(username, email, password, UserRole.ADMIN);
        this.managedSecurityPolicies = new ArrayList<>();
        this.systemLogs = new ArrayList<>();
    }

    @Override
    public void displayDashboard() {
        System.out.println("=== ADMIN DASHBOARD ===");
        System.out.println("Welcome, " + username);
        System.out.println("Role: " + role);
    }

    @Override
    public boolean performOperation(String operationType) {
        switch (operationType.toUpperCase()) {
            case "MANAGE_USERS":
            case "SECURITY_SETTINGS":
            case "SYSTEM_SETTINGS":
                return true;
            default:
                return false;
        }
    }

    public void addSecurityPolicy(String policy) {
        managedSecurityPolicies.add(policy);
    }

    public List<String> getSecurityPolicies() {
        return new ArrayList<>(managedSecurityPolicies);
    }

    public void addSystemLog(String log) {
        systemLogs.add(log);
    }

    public List<String> getSystemLogs() {
        return new ArrayList<>(systemLogs);
    }
}
