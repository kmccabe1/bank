package com.revature.repository;

import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exception.AccountSQLException;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            preparedStatement.setString(3, resultSet.getString("userId"));
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                account.setId(generatedKeys.getInt(1));
                return account;
            }
            throw new AccountSQLException("Account could not be created: please try again");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public Account getAccount(int accountId, User user) {
        String sql = "SELECT * FROM Accounts WHERE accountId = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(accountId));
            ResultSet resultSet = preparedStatement.executeQuery();
            String accountType = resultSet.getString("accountType");
            double balance = resultSet.getDouble("balance");
            return new Account(accountType, balance, user);
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public void updateAccountBalance(int accountId, double balance) {
        String sql = "UPDATE Accounts SET balance = ? WHERE accountId = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Double.toString(balance));
            preparedStatement.setString(2, Integer.toString(accountId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        String sql = "DELETE FROM Accounts WHERE accountId = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(accountId));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    @Override
    public boolean validateOwner(int accountId, String username) {
        // Check if account belongs to user
        int userId = getUserId(username);
        String sql = "SELECT userId FROM Accounts WHERE accountId = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Integer.toString(accountId));
            ResultSet resultSet = preparedStatement.executeQuery();
            return userId == resultSet.getInt("userId");
        } catch (SQLException e) {
            throw new AccountSQLException(e.getMessage());
        }
    }

    private int getUserId(String username) {
        // Retrieve userId from DB for account owner
        String sql = "SELECT userId FROM Users WHERE username = ?";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt("userId");
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }
}
