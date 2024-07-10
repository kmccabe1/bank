package com.revature.repository;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDao implements UserDao {
    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO User VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return user;
            }
            throw new UserSQLException("User could not be created: please try again");
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM User";
        try (Connection connection = DatabaseConnector.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new UserSQLException(e.getMessage());
        }
    }
}
