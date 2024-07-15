package com.revature.controller;

import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.repository.AccountDao;

import java.util.Map;
import java.util.Scanner;

public class AccountController {
    private final Scanner scanner;
    private final AccountDao accountDao;

    public AccountController(Scanner scanner, AccountDao accountDao) {
        this.scanner = scanner;
        this.accountDao = accountDao;
    }

    public void promptUser(User user, Map<String, String> map) {
        System.out.println("What would you like to do?");
        System.out.println("1. Register a bank account");
        System.out.println("2. Get Cash");
        System.out.println("3. Deposit");
        System.out.println("4. View Account");
        System.out.println("5. Close a bank account");
        System.out.println("q. Logout");
        try {
            String action = scanner.nextLine();
            switch (action) {
                case "1" -> {
                    // Register a new bank account
                    Account account = registerNewAccount(user);
                    System.out.println("Your " + account.getType() + " account has been created");
                    System.out.println("Account ID: " + account.getId());
                    String balance = Account.df.format(account.getBalance());
                    if (balance.equals("-0.00")) {
                        System.out.println("Available balance: 0.00");
                    } else {
                        System.out.println("Available balance: " + Account.df.format(account.getBalance()));
                    }
                }
                case "2" -> {
                    // Withdraw from a bank account
                    System.out.print("Please enter your account ID: ");
                    int accountId = Integer.parseInt(scanner.nextLine());
                    if (validateOwner(accountId, user.getUsername())) {
                        // User owns account
                        System.out.print("Please enter the amount you would like to withdraw: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        double balance = withdraw(accountDao.getAccount(accountId, user), accountId, amount);
                        // Format output to 2 decimal places
                        String str = Account.df.format(balance);
                        if (str.equals("-0.00")) {
                            // Handle negative zero rounding precision error
                            System.out.println("Available balance: 0.00");
                        } else {
                            System.out.println("Available balance: " + str);
                        }
                    } else {
                        System.out.println("You are not the owner of this account");
                    }
                }
                case "3" -> {
                    // Deposit to a bank account
                    System.out.print("Please enter your account ID: ");
                    int accountId = Integer.parseInt(scanner.nextLine());
                    if (validateOwner(accountId, user.getUsername())) {
                        // User owns account
                        System.out.print("Please enter the amount you would like to deposit: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        double balance = deposit(accountDao.getAccount(accountId, user), accountId, amount);
                        // Format output to 2 decimal places
                        String str = Account.df.format(balance);
                        if (str.equals("-0.00")) {
                            // Handle negative zero rounding precision error
                            System.out.println("Available balance: 0.00");
                        } else {
                            System.out.println("Available balance: " + str);
                        }
                    } else {
                        System.out.println("You are not the owner of this account");
                    }
                }
                case "4" -> {
                    // View bank account details
                    System.out.print("Please enter your account ID: ");
                    int accountId = Integer.parseInt(scanner.nextLine());
                    if (validateOwner(accountId, user.getUsername())) {
                        // Only show account details if user owns account
                        Account account = accountDao.getAccount(accountId, user);
                        System.out.println("Owner: " + account.getOwner().getUsername());
                        System.out.println("Account Type: " + account.getType());
                        String balance = Account.df.format(account.getBalance());
                        if (balance.equals("-0.00")) {
                            System.out.println("Available balance: 0.00");
                        } else {
                            System.out.println("Available balance: " + Account.df.format(account.getBalance()));
                        }
                    } else {
                        System.out.println("You are not the owner of this account");
                    }
                }
                case "5" -> {
                    // Close bank account
                    System.out.print("Please enter your account ID: ");
                    int accountId = Integer.parseInt(scanner.nextLine());
                    if (validateOwner(accountId, user.getUsername())) {
                        accountDao.deleteAccount(accountId);
                        System.out.println("Your account has been closed");
                    } else {
                        System.out.println("You are not the owner of this account");
                    }
                }
                case "q" -> {
                    // Logout user by removing user from map
                    map.remove("User");
                    System.out.println("Logging out...");
                }
            }
        } catch (LoginFail e) {
            System.out.println(e.getMessage());
        }
    }

    public Account registerNewAccount(User user) {
        // Create a new bank account and insert into DB
        System.out.print("Please enter an account type (checking, savings): ");
        String type = scanner.nextLine();
        System.out.print("Please enter the amount you would like to deposit: ");
        double balance = Double.parseDouble(scanner.nextLine());
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

    public boolean validateOwner(int accountId, String username) {
        // Query DB to confirm bank account is owned by user
        return accountDao.validateOwner(accountId, username);
    }
}
