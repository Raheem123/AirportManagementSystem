package com.airportmanagement.dao;

import com.airportmanagement.config.DatabaseConnection;
import com.airportmanagement.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private static final String SELECT_ALL =
            "SELECT staff_id, first_name, last_name, email, phone, job_title FROM staff ORDER BY staff_id";
    private static final String INSERT =
            "INSERT INTO staff (first_name, last_name, email, phone, job_title) VALUES (?, ?, ?, ?, ?)";

    public List<Staff> findAll() throws SQLException {
        List<Staff> staffMembers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                staffMembers.add(mapRow(resultSet));
            }
        }
        return staffMembers;
    }

    public int create(Staff staff) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, staff.getFirstName());
            statement.setString(2, staff.getLastName());
            statement.setString(3, staff.getEmail());
            statement.setString(4, emptyToNull(staff.getPhone()));
            statement.setString(5, staff.getJobTitle());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("Staff member was created but no ID was returned.");
    }

    private Staff mapRow(ResultSet resultSet) throws SQLException {
        return new Staff(resultSet.getInt("staff_id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"), resultSet.getString("email"),
                resultSet.getString("phone"), resultSet.getString("job_title"));
    }

    private String emptyToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }
}
