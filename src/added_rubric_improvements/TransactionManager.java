package added_rubric_improvements;

import java.sql.Connection;
import java.sql.SQLException;

import database.DatabaseConfig;

public class TransactionManager {
    public static <T> T runInTransaction(TransactionCallable<T> callable) throws SQLException {
        Connection conn = null;
        boolean oldAuto = true;
        try {
            conn = DatabaseConfig.getConnection();
            oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);
            T res = callable.call(conn);
            conn.commit();
            return res;
        } catch(SQLException e) {
            if (conn!=null) try { conn.rollback(); } catch(SQLException ex) {}
            throw e;
        } finally {
            if (conn!=null) try { conn.setAutoCommit(oldAuto); conn.close(); } catch(SQLException ex) {}
        }
    }
}

interface TransactionCallable<T> {
    T call(Connection conn) throws SQLException;
}
