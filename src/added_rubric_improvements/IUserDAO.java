package added_rubric_improvements;

import java.sql.SQLException;
import java.util.List;
import models.User;

public interface IUserDAO {
    User findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
    boolean save(User user) throws SQLException;
    boolean update(User user) throws SQLException;
    boolean delete(int id) throws SQLException;
}
