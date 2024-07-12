package com.revature.controller;

import com.revature.exception.LoginFail;
import com.revature.exception.UserValidationFail;
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
                case "1" -> {
                    try {
                        registerNewUser();
                    } catch (UserValidationFail e) {
                        System.out.println("Invalid username or password provided");
                        System.out.println("Username and password must not be longer than 30 characters");
                    }
                }
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

    public void registerNewUser() {
        User user = userService.validateUser(getUserCredentials());
        System.out.printf("New account created: %s\n", user);
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
