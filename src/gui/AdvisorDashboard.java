package gui;

import javax.swing.*;
import java.awt.*;
import models.FinancialAdvisor;
import services.FinanceService;

public class AdvisorDashboard extends JFrame {
    private FinancialAdvisor advisor;
    private FinanceService financeService;

    public AdvisorDashboard(FinancialAdvisor advisor, FinanceService financeService) {
        this.advisor = advisor;
        this.financeService = financeService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Financial Advisor Dashboard - " + advisor.getUsername());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Overview", createOverviewTab());
        tabbedPane.addTab("Provide Advice", createAdviceTab());
        tabbedPane.addTab("User Interactions", createInteractionTab());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createOverviewTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel welcomeLabel = new JLabel("Welcome, " + advisor.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        JLabel assignedLabel = new JLabel("Assigned Users: " + advisor.getAssignedUsers().size());
        gbc.gridy = 1;
        panel.add(assignedLabel, gbc);

        JLabel roleLabel = new JLabel("Role: " + advisor.getRole());
        gbc.gridy = 2;
        panel.add(roleLabel, gbc);

        return panel;
    }

    private JPanel createAdviceTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userIdLabel = new JLabel("User ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userIdLabel, gbc);

        JTextField userIdField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(userIdField, gbc);

        JLabel adviceLabel = new JLabel("Advice:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(adviceLabel, gbc);

        JTextArea adviceArea = new JTextArea(10, 40);
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(new JScrollPane(adviceArea), gbc);

        JButton sendButton = new JButton("Send Advice");
        sendButton.addActionListener(e -> {
            String userIdStr = userIdField.getText();
            String advice = adviceArea.getText();
            if (!userIdStr.isEmpty() && !advice.isEmpty()) {
                try {
                    int userId = Integer.parseInt(userIdStr);
                    advisor.addAdvice(userId, advice);
                    JOptionPane.showMessageDialog(this, "Advice sent successfully!");
                    userIdField.setText("");
                    adviceArea.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid User ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(sendButton, gbc);

        return panel;
    }

    private JPanel createInteractionTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea interactionArea = new JTextArea();
        interactionArea.setEditable(false);
        interactionArea.setLineWrap(true);
        interactionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(interactionArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Load Interactions");
        loadButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Assigned Users: \n");
            for (Integer userId : advisor.getAssignedUsers()) {
                sb.append("- User ID: ").append(userId).append("\n");
            }
            interactionArea.setText(sb.toString());
        });

        JPanel topPanel = new JPanel();
        topPanel.add(loadButton);
        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }
}
