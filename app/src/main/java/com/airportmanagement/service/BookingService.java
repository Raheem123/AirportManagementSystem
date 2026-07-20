package com.airportmanagement.service;

import com.airportmanagement.config.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingService {
    private static final String LOCK_FLIGHT_CAPACITY =
            "SELECT aircraft.capacity "
                    + "FROM flight_instance "
                    + "JOIN aircraft ON aircraft.aircraft_id = flight_instance.aircraft_id "
                    + "WHERE flight_instance.instance_id = ? FOR UPDATE";

    private static final String COUNT_BOOKED_SEATS =
            "SELECT COUNT(*) FROM ticket_segment "
                    + "WHERE flight_instance_id = ? AND segment_status <> 'CANCELLED'";

    private static final String INSERT_TICKET =
            "INSERT INTO ticket (booking_reference, passenger_id, ticket_status) VALUES (?, ?, 'CONFIRMED')";

    private static final String INSERT_TICKET_SEGMENT =
            "INSERT INTO ticket_segment "
                    + "(ticket_id, flight_instance_id, segment_number, seat_number, cabin_class, ticket_price, segment_status) "
                    + "VALUES (?, ?, 1, ?, ?, ?, 'CONFIRMED')";

    public int createDirectBooking(int passengerId, int flightInstanceId, String bookingReference,
                                   String seatNumber, String cabinClass, BigDecimal ticketPrice)
            throws SQLException {
        validate(passengerId, flightInstanceId, bookingReference, seatNumber, cabinClass, ticketPrice);

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                int capacity = getLockedCapacity(connection, flightInstanceId);
                int bookedSeats = getBookedSeatCount(connection, flightInstanceId);

                if (bookedSeats >= capacity) {
                    throw new IllegalStateException("This flight has no available seats.");
                }

                int ticketId = createTicket(connection, passengerId, bookingReference.trim().toUpperCase());
                createTicketSegment(connection, ticketId, flightInstanceId,
                        seatNumber.trim().toUpperCase(), cabinClass.trim().toUpperCase(), ticketPrice);

                connection.commit();
                return ticketId;
            } catch (Exception exception) {
                connection.rollback();
                if (exception instanceof SQLException) {
                    throw (SQLException) exception;
                }
                if (exception instanceof RuntimeException) {
                    throw (RuntimeException) exception;
                }
                throw new SQLException("Booking failed and was rolled back.", exception);
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private int getLockedCapacity(Connection connection, int flightInstanceId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(LOCK_FLIGHT_CAPACITY)) {
            statement.setInt(1, flightInstanceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("capacity");
                }
            }
        }

        throw new IllegalArgumentException("Selected flight instance does not exist.");
    }

    private int getBookedSeatCount(Connection connection, int flightInstanceId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(COUNT_BOOKED_SEATS)) {
            statement.setInt(1, flightInstanceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    private int createTicket(Connection connection, int passengerId, String bookingReference) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, bookingReference);
            statement.setInt(2, passengerId);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("Ticket was created but no generated ID was returned.");
    }

    private void createTicketSegment(Connection connection, int ticketId, int flightInstanceId,
                                     String seatNumber, String cabinClass, BigDecimal ticketPrice)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_SEGMENT)) {
            statement.setInt(1, ticketId);
            statement.setInt(2, flightInstanceId);
            statement.setString(3, seatNumber);
            statement.setString(4, cabinClass);
            statement.setBigDecimal(5, ticketPrice);
            statement.executeUpdate();
        }
    }

    private void validate(int passengerId, int flightInstanceId, String bookingReference,
                          String seatNumber, String cabinClass, BigDecimal ticketPrice) {
        if (passengerId <= 0 || flightInstanceId <= 0) {
            throw new IllegalArgumentException("Passenger and flight selection are required.");
        }
        if (bookingReference == null || !bookingReference.trim().matches("[A-Za-z0-9]{8}")) {
            throw new IllegalArgumentException("Booking reference must contain exactly 8 letters or numbers.");
        }
        if (seatNumber == null || seatNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Seat number is required.");
        }
        if (cabinClass == null || !cabinClass.trim().matches("(?i)ECONOMY|BUSINESS|FIRST")) {
            throw new IllegalArgumentException("Cabin class must be Economy, Business, or First.");
        }
        if (ticketPrice == null || ticketPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ticket price must be zero or greater.");
        }
    }
}
