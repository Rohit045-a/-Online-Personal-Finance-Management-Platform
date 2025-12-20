package com.fullscore.service.impl;
import com.fullscore.service.UserService;
import com.fullscore.model.User;
import java.util.*;

public class UserServiceImpl implements UserService<User> {
    public void process(List<User> users) {
        users.forEach(u -> System.out.println(u));
    }
}