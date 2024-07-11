package com.revature.repository;

import com.revature.entity.Account;
import com.revature.entity.User;

public interface AccountDao {
    Account createAccount(Account account);

    Account getAccount(int accountId, User user);

    double updateAccountBalance(int accountId, double amount);

    void deleteAccount(int accountId);

}
