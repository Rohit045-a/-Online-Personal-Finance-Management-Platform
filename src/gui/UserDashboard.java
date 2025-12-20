package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import models.RegularUser;
import models.Expense;
import models.Budget;
import services.FinanceService;

public class UserDashboard extends JFrame {
    private RegularUser user;
    private FinanceService financeService;
    private JTable expenseTable;
    private JTable budgetTable;
    private JLabel totalExpensesLabel;

    public UserDashboard(RegularUser user, FinanceService financeService) {
        this.user = user;
        this.financeService = financeService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Dashboard - " + user.getUsername());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Expense Tracker", createExpenseTab());
        tabbedPane.addTab("Budget Management", createBudgetTab());
        tabbedPane.addTab("Financial Advice", createAdviceTab());
        tabbedPane.addTab("Profile", createProfileTab());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createExpenseTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(categoryLabel, gbc);

        JTextField categoryField = new JTextField(10);
        gbc.gridx = 1;
        inputPanel.add(categoryField, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 2;
        inputPanel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(10);
        gbc.gridx = 3;
        inputPanel.add(amountField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridx = 4;
        inputPanel.add(descriptionLabel, gbc);

        JTextField descriptionField = new JTextField(15);
        gbc.gridx = 5;
        inputPanel.add(descriptionField, gbc);

        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (category.isEmpty() || amount <= 0 || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields correctly!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                financeService.addExpense(user.getUserId(), category, amount, description);
                JOptionPane.showMessageDialog(this, "Expense added successfully!");

                categoryField.setText("");
                amountField.setText("");
                descriptionField.setText("");

                refreshExpenseTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 6;
        inputPanel.add(addButton, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);

        expenseTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        totalExpensesLabel = new JLabel("Total Expenses: $0.00");
        totalExpensesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(totalExpensesLabel);
        panel.add(infoPanel, BorderLayout.SOUTH);

        refreshExpenseTable();
        return panel;
    }

    private JPanel createBudgetTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField(15);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        JButton setButton = new JButton("Set Budget");

        setButton.addActionListener(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());

                if (category.isEmpty() || amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields correctly!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                financeService.setBudget(user.getUserId(), category, amount);
                JOptionPane.showMessageDialog(this, "Budget set successfully!");
                categoryField.setText("");
                amountField.setText("");
                refreshBudgetTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(setButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        budgetTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(budgetTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        refreshBudgetTable();
        return panel;
    }

    private JPanel createAdviceTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea adviceArea = new JTextArea();
        adviceArea.setEditable(false);
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);
        adviceArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton generateButton = new JButton("Generate Advice");
        generateButton.addActionListener(e -> {
            try {
                java.util.List<String> advice = financeService.generateFinancialAdvice(user.getUserId());
                StringBuilder sb = new StringBuilder();
                for (String adv : advice) {
                    sb.append("• ").append(adv).append("\n\n");
                }
                adviceArea.setText(sb.toString());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(generateButton);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(adviceArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProfileTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Username: " + user.getUsername());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);

        JLabel roleLabel = new JLabel("Role: " + user.getRole());
        gbc.gridy = 2;
        panel.add(roleLabel, gbc);

        return panel;
    }

    private void refreshExpenseTable() {
        try {
            java.util.List<Expense> expenses = financeService.getUserExpenses(user.getUserId());
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Category");
            model.addColumn("Amount");
            model.addColumn("Description");
            model.addColumn("Date");

            double total = 0;
            for (Expense expense : expenses) {
                model.addRow(new Object[]{
                        expense.getCategory(),
                        String.format("$%.2f", expense.getAmount()),
                        expense.getDescription(),
                        expense.getExpenseDate()
                });
                total += expense.getAmount();
            }

            expenseTable.setModel(model);
            totalExpensesLabel.setText(String.format("Total Expenses: $%.2f", total));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshBudgetTable() {
        try {
            java.util.List<Budget> budgets = financeService.getUserBudgets(user.getUserId());
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Category");
            model.addColumn("Amount");
            model.addColumn("Created Date");

            for (Budget budget : budgets) {
                model.addRow(new Object[]{
                        budget.getCategory(),
                        String.format("$%.2f", budget.getAmount()),
                        budget.getCreatedDate()
                });
            }

            budgetTable.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
