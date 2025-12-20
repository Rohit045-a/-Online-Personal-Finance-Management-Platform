package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import models.*;
import services.FinanceService;

public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private FinanceService financeService;

    public RegisterDialog(JFrame parent, FinanceService financeService) {
        super(parent, "Register New User", true);
        this.financeService = financeService;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Role
        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);
        roleCombo = new JComboBox<>(new String[]{"USER", "ADVISOR", "ADMIN"});
        gbc.gridx = 1;
        panel.add(roleCombo, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> performRegistration());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel);
    }

    private void performRegistration() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String roleStr = (String) roleCombo.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User.UserRole role = User.UserRole.valueOf(roleStr);
            User newUser = createUser(username, email, password, role);
            financeService.registerUser(newUser);
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User createUser(String username, String email, String password, User.UserRole role) {
        switch (role) {
            case ADMIN:
                return new Admin(username, email, password);
            case ADVISOR:
                return new FinancialAdvisor(username, email, password);
            case USER:
                return new RegularUser(username, email, password);
            default:
                return new RegularUser(username, email, password);
        }
    }
}
