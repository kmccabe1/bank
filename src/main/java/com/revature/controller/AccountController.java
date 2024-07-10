package com.revature.controller;

import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exception.LoginFail;

import java.util.Map;
import java.util.Scanner;

public class AccountController {
    private final Scanner scanner;

    public AccountController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void promptUser(User user) {
        System.out.println("What would you like to do?");
        System.out.println("1. Register a bank account");
        System.out.println("2. Withdraw money");
        System.out.println("3. Deposit money");
        System.out.println("q. Logout");
        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> {
                    registerNewAccount(user);
                }
                case "2" -> {

                }
                case "3" -> {

                }
                case "q" -> {
                    System.out.println("Goodbye!");
                }
            }
        } catch (LoginFail e) {
            System.out.println(e.getMessage());
        }
    }

    public Account registerNewAccount(User user) {
        System.out.print("Please enter an account type (checking, savings): ");
        String type = scanner.nextLine();
        System.out.print("Please enter the amount you would like to deposit: ");
        double balance = scanner.nextDouble();
        Account account = new Account(type, balance, user);

        return account;
    }
}
