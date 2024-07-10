package com.revature.repository;

import com.revature.entity.User;

import java.util.ArrayList;
import java.util.List;

public class InMemUser implements UserDao {
    private List<User> users;

    public InMemUser() {
        users = new ArrayList<>();
        users.add(new User("admin", "1234"));
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public User createUser(User user) {
        users.add(user);
        return user;
    }
}
