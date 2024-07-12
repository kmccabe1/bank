package com.revature.entity;

import java.text.DecimalFormat;

public class Account {
    private final String type;
    private double balance;
    private final User owner;
    public static final DecimalFormat df = new DecimalFormat("#0.00");

    public Account(String type, double balance, User owner) {
        this.type = type;
        this.balance = balance;
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public double deposit(double amount) {
        // Deposit amount to account and format to 2 decimal places
        balance = balance + amount;
        return balance;
    }

    public double withdraw(double amount) {
        // Withdraw amount from account if balance will not fall below $0
        if (balance - amount >= 0) {
            balance = balance - amount;
        }
        return balance;
    }
}
