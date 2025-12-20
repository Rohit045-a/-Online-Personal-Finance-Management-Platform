package com.fullscore.dao.impl;

import com.fullscore.dao.UserDAO;
import com.fullscore.model.User;
import com.fullscore.util.DBUtil;
import java.sql.*;

public class UserDAOImpl implements UserDAO {
    public void save(User user) {
        try(Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username) VALUES(?)");
            ps.setString(1, user.username);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public User findByUsername(String username) {
        return new User(1, username);
    }
}