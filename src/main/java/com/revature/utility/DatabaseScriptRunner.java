package com.revature.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DatabaseScriptRunner {

    public static void main(String[] args) {
        try {
            Path sqlPath = Paths.get("src/main/resources/bank_setup_reset_script.sql");
            try (
                    Connection connection = DatabaseConnector.createConnection();
                    Stream<String> lines = Files.lines(sqlPath)
            ) {
                // Enables an ALL or NOTHING transaction structure
                connection.setAutoCommit(false);
                StringBuilder sqlBuilder = new StringBuilder();
                lines.forEach(line -> sqlBuilder.append(line));
                String sql = sqlBuilder.toString();
                // Separate SQL into individual statements
                String[] statements = sql.split(";\\R");
                for (String statement : statements) {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(statement);
                }
                connection.commit();
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
