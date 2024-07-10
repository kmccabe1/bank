package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.repository.UserDao;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User validateUser(User user) {
        if (checkLength(user)) {
            if (checkUnique(user)) {
                return userDao.createUser(user);
            }
        }
        throw new RuntimeException("placeholder for custom exception");
    }

    private boolean checkLength(User user) {
        return user.getUsername().length() <= 30 && user.getPassword().length() <= 30;
    }

    private boolean checkUnique(User user) {
        boolean unique = true;
        List<User> users = userDao.getUsers();
        for (User u : users) {
            if (user.getUsername().equals(u.getUsername())) {
                unique = false;
                break;
            }
        }
        return unique;
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
