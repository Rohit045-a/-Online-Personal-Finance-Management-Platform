package added_rubric_improvements;

import java.sql.SQLException;
import java.util.List;
import models.Expense;

public class ExpenseDAOAdapter implements IExpenseDAO {
    private dao.ExpenseDAO impl = null;
    public ExpenseDAOAdapter() {
        try { impl = new dao.ExpenseDAO(); } catch(Throwable t) { impl = null; }
    }
    public Expense findById(int id) throws SQLException {
        if (impl==null) throw new SQLException("Underlying ExpenseDAO not available");
        return impl.findById(id);
    }
    public List<Expense> findByUserId(int userId) throws SQLException { return impl.findByUserId(userId); }
    public boolean save(Expense exp) throws SQLException { return impl.save(exp); }
    public boolean update(Expense exp) throws SQLException { return impl.update(exp); }
    public boolean delete(int id) throws SQLException { return impl.delete(id); }
}
