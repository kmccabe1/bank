package com.revature.controller;

import com.revature.exception.LoginFail;
import com.revature.service.UserService;
import com.revature.entity.User;

import java.util.Map;
import java.util.Scanner;

public class UserController {
    private final Scanner scanner;
    private final UserService userService;

    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void promptUser(Map<String, String> map) {
        System.out.println("What would you like to do?");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> registerNewUser();
                case "2" -> {
                    map.put("User", login().getUsername());
                }
                case "q" -> {
                    System.out.println("Goodbye!");
                    map.put("Continue Loop", "false");
                }
            }
        } catch (LoginFail e) {
            System.out.println(e.getMessage());
        }
    }

    public User registerNewUser() {
        User user = userService.validateUser(getUserCredentials());
        System.out.printf("New account created: %s\n", user);
        return user;
    }

    public User login() {
        return userService.checkLogin(getUserCredentials());
    }

    public User getUserCredentials() {
        System.out.print("Please enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Please enter a password: ");
        String password = scanner.nextLine();
        return new User(username, password);
    }
}
