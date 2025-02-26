package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.UserValidationFail;
import com.revature.repository.UserDao;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User validateUser(User user) {
        if (checkLength(user)) {
            if (checkUnique(user)) {
                return userDao.createUser(user);
            } else {
                throw new UserValidationFail("Username must be unique");
            }
        }
        throw new UserValidationFail("Username and password must not be longer than 30 characters");
    }

    private boolean checkLength(User user) {
        return user.getUsername().length() <= 30 && user.getPassword().length() <= 30;
    }

    private boolean checkUnique(User user) {
        List<User> users = userDao.getUsers();
        for (User u : users) {
            if (user.getUsername().equals(u.getUsername())) {
                return false;
            }
        }
        return true;
    }

    public User checkLogin(User user) {
        for (User u : userDao.getUsers()) {
            boolean userMatch = u.getUsername().equals(user.getUsername());
            boolean passMatch = u.getPassword().equals(user.getPassword());
            if (userMatch && passMatch) {
                return user;
            }
        }
        throw new LoginFail("Credentials are invalid: please try again");
    }
}
