package added_rubric_improvements;

import java.sql.SQLException;
import java.util.List;
import models.User;

// Adapter: tries to use existing src.dao.UserDAO if available.
public class UserDAOAdapter implements IUserDAO {
    private dao.UserDAO impl = null;
    public UserDAOAdapter() {
        try { impl = new dao.UserDAO(); } catch(Throwable t) { impl = null; }
    }
    public User findById(int id) throws SQLException {
        if (impl==null) throw new SQLException("Underlying UserDAO not available");
        return impl.findById(id);
    }
    public List<User> findAll() throws SQLException { return impl.findAll(); }
    public boolean save(User user) throws SQLException { return impl.save(user); }
    public boolean update(User user) throws SQLException { return impl.update(user); }
    public boolean delete(int id) throws SQLException { return impl.delete(id); }
}
