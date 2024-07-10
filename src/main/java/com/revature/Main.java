package com.revature;

import com.revature.controller.UserController;
import com.revature.repository.SqliteUserDao;
import com.revature.repository.UserDao;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Register a new User
        try (Scanner scanner = new Scanner(System.in)) {
            UserDao userDao = new SqliteUserDao();
            UserService userService = new UserService(userDao);
            UserController userController = new UserController(scanner, userService);
            Map<String, String> map = new HashMap<>();
            map.put("Continue Loop", "true");
            while (Boolean.parseBoolean(map.get("Continue Loop"))) {
                userController.promptUser(map);
                if (map.containsKey("User")) {
                    System.out.println("Banking stuff for user can happen here!");
                    String input = scanner.nextLine();
                }
            }
        }
    }
}