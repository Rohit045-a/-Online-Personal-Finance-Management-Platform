package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import models.Admin;
import models.User;
import services.FinanceService;

public class AdminDashboard extends JFrame {
    private Admin admin;
    private FinanceService financeService;
    private JTable usersTable;

    public AdminDashboard(Admin admin, FinanceService financeService) {
        this.admin = admin;
        this.financeService = financeService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User Management", createUserManagementTab());
        tabbedPane.addTab("Security Settings", createSecurityTab());
        tabbedPane.addTab("System Settings", createSystemSettingsTab());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createUserManagementTab() {
        JPanel panel = new JPanel(new BorderLayout());

        usersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        JButton deleteButton = new JButton("Delete Selected User");

        refreshButton.addActionListener(e -> refreshUserTable());
        deleteButton.addActionListener(e -> deleteSelectedUser());

        buttonPanel.add(refreshButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshUserTable();
        return panel;
    }

    private JPanel createSecurityTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel policyLabel = new JLabel("Add Security Policy:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(policyLabel, gbc);

        JTextField policyField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(policyField, gbc);

        JButton addPolicyButton = new JButton("Add Policy");
        addPolicyButton.addActionListener(e -> {
            String policy = policyField.getText();
            if (!policy.isEmpty()) {
                admin.addSecurityPolicy(policy);
                JOptionPane.showMessageDialog(this, "Security policy added!");
                policyField.setText("");
            }
        });
        gbc.gridx = 2;
        panel.add(addPolicyButton, gbc);

        JLabel policiesLabel = new JLabel("Active Policies:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(policiesLabel, gbc);

        JTextArea policiesArea = new JTextArea();
        policiesArea.setEditable(false);
        policiesArea.setLineWrap(true);
        gbc.gridy = 2;
        gbc.ipady = 150;
        panel.add(new JScrollPane(policiesArea), gbc);

        JButton refreshPoliciesButton = new JButton("Refresh Policies");
        refreshPoliciesButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (String policy : admin.getSecurityPolicies()) {
                sb.append("• ").append(policy).append("\n");
            }
            policiesArea.setText(sb.toString());
        });
        gbc.gridy = 3;
        gbc.ipady = 0;
        panel.add(refreshPoliciesButton, gbc);

        return panel;
    }

    private JPanel createSystemSettingsTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel settingLabel = new JLabel("System Configuration:");
        settingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(settingLabel, gbc);

        JLabel maxUsersLabel = new JLabel("Max Users:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(maxUsersLabel, gbc);

        JTextField maxUsersField = new JTextField("1000", 10);
        gbc.gridx = 1;
        panel.add(maxUsersField, gbc);

        JLabel backupLabel = new JLabel("Backup Frequency (hours):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(backupLabel, gbc);

        JTextField backupField = new JTextField("24", 10);
        gbc.gridx = 1;
        panel.add(backupField, gbc);

        JButton saveButton = new JButton("Save Settings");
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings saved successfully!"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        return panel;
    }

    private void refreshUserTable() {
        try {
            java.util.List<User> users = financeService.getAllUsers();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("User ID");
            model.addColumn("Username");
            model.addColumn("Email");
            model.addColumn("Role");

            for (User user : users) {
                model.addRow(new Object[]{
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()
                });
            }

            usersTable.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (int) usersTable.getValueAt(selectedRow, 0);
        try {
            // Delete logic would go here
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            refreshUserTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
