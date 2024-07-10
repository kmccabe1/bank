package com.revature.repository;

import com.revature.entity.User;

import java.util.List;

public interface UserDao {
    User createUser(User user);

    List<User> getUsers();
}
