package com.fullscore.service;
import com.fullscore.model.User;
import java.util.List;

public interface UserService<T extends User> {
    void process(List<T> users);
}