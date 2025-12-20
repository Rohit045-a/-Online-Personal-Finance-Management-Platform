package added_rubric_improvements;

public class DAOFactory {
    // This factory would return interface-typed DAO implementations.
    // For the project, it's a placeholder to show programming-to-interfaces.
    public static IUserDAO getUserDAO() {
        return new UserDAOAdapter();
    }
    public static IExpenseDAO getExpenseDAO() {
        return new ExpenseDAOAdapter();
    }
}
