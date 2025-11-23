package added_rubric_improvements;

import java.sql.SQLException;
import java.util.List;
import models.Expense;

public interface IExpenseDAO {
    Expense findById(int id) throws SQLException;
    List<Expense> findByUserId(int userId) throws SQLException;
    boolean save(Expense exp) throws SQLException;
    boolean update(Expense exp) throws SQLException;
    boolean delete(int id) throws SQLException;
}
