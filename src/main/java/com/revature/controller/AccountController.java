package com.revature.controller;

import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.repository.AccountDao;

import java.util.Scanner;

public class AccountController {
    private final Scanner scanner;
    private final AccountDao accountDao;

    public AccountController(Scanner scanner, AccountDao accountDao) {
        this.scanner = scanner;
        this.accountDao = accountDao;
    }

    public void promptUser(User user) {
        System.out.println("What would you like to do?");
        System.out.println("1. Register a bank account");
        System.out.println("2. Get Cash");
        System.out.println("3. Deposit");
        System.out.println("q. Logout");
        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> {
                    Account account = registerNewAccount(user);
                    System.out.println("Your " + account.getType() + " account has been created");
                    System.out.println("Available balance: " + account.getBalance());
                }
                case "2" -> {
                    System.out.println("Please enter your account ID: ");
                    int accountId = scanner.nextInt();
                    System.out.println("Please enter the amount you would like to withdraw: ");
                    double amount = scanner.nextDouble();
                    double balance = withdraw(accountDao.getAccount(accountId, user), accountId, amount);
                    System.out.println("Available balance: " + balance);
                }
                case "3" -> {
                    System.out.println("Please enter your account ID: ");
                    int accountId = scanner.nextInt();
                    System.out.println("Please enter the amount you would like to deposit: ");
                    double amount = scanner.nextDouble();
                    double balance = deposit(accountDao.getAccount(accountId, user),accountId, amount);
                    System.out.println("Available balance: " + balance);
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
        return accountDao.createAccount(account);
    }

    public double withdraw(Account bankAccount, int accountId, double amount) {
        // Withdraw amount from bankAccount and update DB
        double balance = bankAccount.withdraw(amount);
        accountDao.updateAccountBalance(accountId, balance);
        return balance;
    }

    public double deposit(Account bankAccount, int accountId, double amount) {
        // Deposit amount to bankAccount and update DB
        double balance = bankAccount.deposit(amount);
        accountDao.updateAccountBalance(accountId, balance);
        return balance;
    }
}
