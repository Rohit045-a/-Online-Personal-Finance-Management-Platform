package added_rubric_improvements;

import java.util.concurrent.*;
import java.util.List;
import models.Expense;

public class AsyncExportTask {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    public Future<Boolean> exportExpensesAsync(int userId) {
        return executor.submit(() -> {
            // Simulate export long-running task: fetch expenses and write CSV
            try {
                IExpenseDAO dao = DAOFactory.getExpenseDAO();
                List<Expense> list = dao.findByUserId(userId);
                // write to CSV under project root
                java.io.File f = new java.io.File("exports_user_"+userId+".csv");
                try (java.io.PrintWriter pw = new java.io.PrintWriter(f)) {
                    pw.println("id,date,amount,category,description");
                    for (Expense e : list) {
                        pw.println(e.getId()+","+e.getDate()+","+e.getAmount()+","+e.getCategory()+","+e.getDescription());
                    }
                }
                return true;
            } catch(Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }
    public void shutdown() { executor.shutdown(); }
}
