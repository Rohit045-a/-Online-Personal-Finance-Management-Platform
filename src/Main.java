import javax.swing.*;
import database.DatabaseConfig;
import gui.LoginFrame;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database
            DatabaseConfig.initializeDatabase();
            
            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                new LoginFrame();
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database initialization error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
