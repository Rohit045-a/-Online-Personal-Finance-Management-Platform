package com.fullscore.dao;
import com.fullscore.model.User;

public interface UserDAO {
    void save(User user);
    User findByUsername(String username);
}