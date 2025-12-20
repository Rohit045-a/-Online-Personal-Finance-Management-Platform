package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import models.User;
import services.FinanceService;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private FinanceService financeService;

    public LoginFrame() {
        this.financeService = new FinanceService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Online Personal Finance Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title
        JLabel titleLabel = new JLabel("Finance Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegisterDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            User user = financeService.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                openUserDashboard(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterDialog() {
        RegisterDialog registerDialog = new RegisterDialog(this, financeService);
        registerDialog.setVisible(true);
    }

    private void openUserDashboard(User user) {
        switch (user.getRole()) {
            case ADMIN:
                new AdminDashboard((Admin) user, financeService);
                break;
            case ADVISOR:
                new AdvisorDashboard((FinancialAdvisor) user, financeService);
                break;
            case USER:
                new UserDashboard((RegularUser) user, financeService);
                break;
        }
    }
}
