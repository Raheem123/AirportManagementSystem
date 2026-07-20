package com.airportmanagement.dao;

import com.airportmanagement.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlightStaffAssignmentDao {
    public void assign(int staffId, int flightInstanceId, String duty) throws SQLException {
        String sql = "INSERT INTO flight_staff_assignment (staff_id, flight_instance_id, duty) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            statement.setInt(2, flightInstanceId);
            statement.setString(3, duty.trim());
            statement.executeUpdate();
        }
    }
}
