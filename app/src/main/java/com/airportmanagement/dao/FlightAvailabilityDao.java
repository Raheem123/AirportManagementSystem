package com.airportmanagement.dao;

import com.airportmanagement.config.DatabaseConnection;
import com.airportmanagement.view.FlightAvailability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FlightAvailabilityDao {
    private static final String SELECT_AVAILABILITY =
            "SELECT fi.instance_id, CONCAT(fs.airline_code, fs.flight_number) AS flight_code, "
                    + "CONCAT(departure_airport.iata_code, ' -> ', arrival_airport.iata_code) AS route, "
                    + "fi.scheduled_departure_time, aircraft.model, aircraft.capacity, "
                    + "SUM(CASE WHEN ticket_segment.segment_status <> 'CANCELLED' "
                    + "THEN 1 ELSE 0 END) AS booked_seats "
                    + "FROM flight_instance fi "
                    + "JOIN flight_schedule fs ON fs.schedule_id = fi.schedule_id "
                    + "JOIN airport departure_airport ON departure_airport.airport_id = fs.departure_airport_id "
                    + "JOIN airport arrival_airport ON arrival_airport.airport_id = fs.arrival_airport_id "
                    + "JOIN aircraft ON aircraft.aircraft_id = fi.aircraft_id "
                    + "LEFT JOIN ticket_segment ON ticket_segment.flight_instance_id = fi.instance_id "
                    + "GROUP BY fi.instance_id, fs.airline_code, fs.flight_number, "
                    + "departure_airport.iata_code, arrival_airport.iata_code, "
                    + "fi.scheduled_departure_time, aircraft.model, aircraft.capacity "
                    + "ORDER BY fi.scheduled_departure_time";

    public List<FlightAvailability> findAll() throws SQLException {
        List<FlightAvailability> flights = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_AVAILABILITY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Timestamp departureTimestamp = resultSet.getTimestamp("scheduled_departure_time");
                flights.add(new FlightAvailability(
                        resultSet.getInt("instance_id"),
                        resultSet.getString("flight_code"),
                        resultSet.getString("route"),
                        departureTimestamp.toLocalDateTime(),
                        resultSet.getString("model"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("booked_seats")
                ));
            }
        }

        return flights;
    }
}
