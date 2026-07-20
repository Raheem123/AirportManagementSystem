package com.airportmanagement.dao;

import com.airportmanagement.config.DatabaseConnection;
import com.airportmanagement.view.BaggageTrackingRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BaggageTrackingDao {
    private static final String FIND_ALL_SQL =
            "SELECT bs.baggage_segment_id, b.tag_number, t.booking_reference, " +
            "ts.segment_number, CONCAT(fs.airline_code, fs.flight_number) AS flight_code, " +
            "CONCAT(departure_airport.iata_code, ' -> ', arrival_airport.iata_code) AS route, " +
            "fi.scheduled_departure_time, bs.load_status, bs.loaded_at, bs.unloaded_at " +
            "FROM baggage_segment bs " +
            "JOIN baggage b ON bs.baggage_id = b.baggage_id " +
            "JOIN ticket t ON b.ticket_id = t.ticket_id " +
            "JOIN ticket_segment ts ON bs.ticket_segment_id = ts.segment_id " +
            "JOIN flight_instance fi ON ts.flight_instance_id = fi.instance_id " +
            "JOIN flight_schedule fs ON fi.schedule_id = fs.schedule_id " +
            "JOIN airport departure_airport ON fs.departure_airport_id = departure_airport.airport_id " +
            "JOIN airport arrival_airport ON fs.arrival_airport_id = arrival_airport.airport_id " +
            "ORDER BY t.booking_reference, ts.segment_number";

    public List<BaggageTrackingRecord> findAll() throws SQLException {
        List<BaggageTrackingRecord> records = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                records.add(new BaggageTrackingRecord(
                        resultSet.getInt("baggage_segment_id"),
                        resultSet.getString("tag_number"),
                        resultSet.getString("booking_reference"),
                        resultSet.getInt("segment_number"),
                        resultSet.getString("flight_code"),
                        resultSet.getString("route"),
                        toLocalDateTime(resultSet.getTimestamp("scheduled_departure_time")),
                        resultSet.getString("load_status"),
                        toLocalDateTime(resultSet.getTimestamp("loaded_at")),
                        toLocalDateTime(resultSet.getTimestamp("unloaded_at"))
                ));
            }
        }
        return records;
    }

    public void updateLoadStatus(int baggageSegmentId, String loadStatus) throws SQLException {
        String sql;
        if ("LOADED".equals(loadStatus)) {
            sql = "UPDATE baggage_segment SET load_status = ?, loaded_at = NOW(), unloaded_at = NULL " +
                    "WHERE baggage_segment_id = ?";
        } else if ("UNLOADED".equals(loadStatus)) {
            sql = "UPDATE baggage_segment SET load_status = ?, unloaded_at = NOW() WHERE baggage_segment_id = ?";
        } else {
            sql = "UPDATE baggage_segment SET load_status = ? WHERE baggage_segment_id = ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loadStatus);
            statement.setInt(2, baggageSegmentId);
            if (statement.executeUpdate() != 1) {
                throw new SQLException("Baggage segment was not found.");
            }
        }
    }

    private static java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
