package added_rubric_improvements;

import java.util.List;
import models.Expense;

public interface IFinanceService {
    List<Expense> getExpensesForUser(int userId);
    boolean addExpense(Expense e);
}
