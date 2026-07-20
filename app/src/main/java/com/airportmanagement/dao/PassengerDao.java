package com.airportmanagement.dao;

import com.airportmanagement.config.DatabaseConnection;
import com.airportmanagement.model.Passenger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerDao {
    private static final String SELECT_ALL =
            "SELECT passenger_id, first_name, last_name, date_of_birth, email, phone, passport_number "
                    + "FROM passenger ORDER BY passenger_id";

    private static final String SELECT_BY_ID =
            "SELECT passenger_id, first_name, last_name, date_of_birth, email, phone, passport_number "
                    + "FROM passenger WHERE passenger_id = ?";

    private static final String INSERT =
            "INSERT INTO passenger (first_name, last_name, date_of_birth, email, phone, passport_number) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE =
            "UPDATE passenger SET first_name = ?, last_name = ?, date_of_birth = ?, "
                    + "email = ?, phone = ?, passport_number = ? WHERE passenger_id = ?";

    private static final String DELETE =
            "DELETE FROM passenger WHERE passenger_id = ?";

    public List<Passenger> findAll() throws SQLException {
        List<Passenger> passengers = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                passengers.add(mapRow(resultSet));
            }
        }

        return passengers;
    }

    public Optional<Passenger> findById(int passengerId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, passengerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    public int create(Passenger passenger) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            setPassengerValues(statement, passenger, false);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int passengerId = generatedKeys.getInt(1);
                    passenger.setPassengerId(passengerId);
                    return passengerId;
                }
            }
        }

        throw new SQLException("Passenger was created but no generated ID was returned.");
    }

    public boolean update(Passenger passenger) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            setPassengerValues(statement, passenger, true);
            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(int passengerId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setInt(1, passengerId);
            return statement.executeUpdate() == 1;
        }
    }

    private void setPassengerValues(PreparedStatement statement, Passenger passenger, boolean includeId)
            throws SQLException {
        statement.setString(1, passenger.getFirstName());
        statement.setString(2, passenger.getLastName());
        statement.setDate(3, Date.valueOf(passenger.getDateOfBirth()));
        statement.setString(4, emptyToNull(passenger.getEmail()));
        statement.setString(5, emptyToNull(passenger.getPhone()));
        statement.setString(6, emptyToNull(passenger.getPassportNumber()));

        if (includeId) {
            statement.setInt(7, passenger.getPassengerId());
        }
    }

    private Passenger mapRow(ResultSet resultSet) throws SQLException {
        return new Passenger(
                resultSet.getInt("passenger_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("date_of_birth").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("passport_number")
        );
    }

    private String emptyToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}
