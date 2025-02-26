package com.revature;

import com.revature.controller.AccountController;
import com.revature.controller.UserController;
import com.revature.repository.AccountDao;
import com.revature.repository.SqliteAccountDao;
import com.revature.repository.SqliteUserDao;
import com.revature.repository.UserDao;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            UserDao userDao = new SqliteUserDao();
            AccountDao accountDao = new SqliteAccountDao();
            UserService userService = new UserService(userDao);
            UserController userController = new UserController(scanner, userService);
            AccountController accountController = new AccountController(scanner, accountDao);
            Map<String, String> map = new HashMap<>();
            map.put("Continue Loop", "true");
            while (Boolean.parseBoolean(map.get("Continue Loop"))) {
                // Check if user is logged in
                if (map.containsKey("User")) {
                    // User is logged in. Prompt user for banking action
                    accountController.promptUser(userDao.getUser(map.get("User")), map);
                } else {
                    // User is not logged in. Prompt user for action
                    userController.promptUser(map);
                }
            }
        }
    }
}