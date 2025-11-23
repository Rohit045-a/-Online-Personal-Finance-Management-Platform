package added_rubric_improvements;

import java.util.List;

public class FinanceServiceImpl implements IFinanceService {
    private final IExpenseDAO expenseDAO;
    public FinanceServiceImpl(IExpenseDAO expenseDAO) {
        this.expenseDAO = expenseDAO;
    }
    public java.util.List<models.Expense> getExpensesForUser(int userId) {
        try {
            return expenseDAO.findByUserId(userId);
        } catch(Exception e) {
            throw new DataAccessException("Failed to load expenses", e);
        }
    }
    public boolean addExpense(models.Expense e) {
        try {
            return expenseDAO.save(e);
        } catch(Exception ex) {
            throw new DataAccessException("Failed to save expense", ex);
        }
    }
}
