package com.revature.repository;

import com.revature.entity.Account;
import com.revature.exception.AccountSQLException;
import com.revature.utility.DatabaseConnector;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class SqliteAccountDao implements AccountDao {
    @Override
    public Account createAccount(Account account) {
        String sql = "INSERT INTO Accounts (accountType, balance, userId) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.createConnection()) {
            // Get userId before creating account
            String query = "SELECT userId FROM Users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account.getOwner().getUsername());
            ResultSet resultSet = statement.executeQuery();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getType());
            preparedStatement.setString(2, Double.toString(account.getBalance()));
            //System.out.println(resultSet.getString("userId"));
            preparedStatement.setString(3, resultSet.getString("userId"));
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return account;
            }
            throw new AccountSQLException("Account could not be created: please try again");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public Account getAccount(Account account) {
        return null;
    }
}
