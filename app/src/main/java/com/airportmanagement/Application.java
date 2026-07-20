package com.airportmanagement;

import com.airportmanagement.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println(
                    "Connection successful. Database: " + connection.getCatalog()
            );
        } catch (SQLException exception) {
            System.err.println("Database connection failed.");
            exception.printStackTrace();
        }
    }
}
