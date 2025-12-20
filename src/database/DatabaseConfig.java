package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:sqlite:finance_db.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    public static void initializeDatabase() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        // Users table
        String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "role TEXT NOT NULL CHECK(role IN ('ADMIN', 'ADVISOR', 'USER'))," +
                "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        stmt.execute(userTable);

        // Expenses table
        String expensesTable = "CREATE TABLE IF NOT EXISTS expenses (" +
                "expense_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "category TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "description TEXT," +
                "expense_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))";
        stmt.execute(expensesTable);

        // Budgets table
        String budgetsTable = "CREATE TABLE IF NOT EXISTS budgets (" +
                "budget_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "category TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))";
        stmt.execute(budgetsTable);

        // Financial Advice table
        String adviceTable = "CREATE TABLE IF NOT EXISTS financial_advice (" +
                "advice_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "advisor_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "advice TEXT NOT NULL," +
                "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(advisor_id) REFERENCES users(user_id)," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))";
        stmt.execute(adviceTable);

        // Feedback table
        String feedbackTable = "CREATE TABLE IF NOT EXISTS feedback (" +
                "feedback_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "message TEXT NOT NULL," +
                "status TEXT DEFAULT 'OPEN'," +
                "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))";
        stmt.execute(feedbackTable);

        stmt.close();
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
