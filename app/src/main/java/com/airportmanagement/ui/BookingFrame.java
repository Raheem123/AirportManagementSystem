package com.airportmanagement.ui;

import com.airportmanagement.dao.FlightAvailabilityDao;
import com.airportmanagement.service.BookingService;
import com.airportmanagement.view.FlightAvailability;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

public class BookingFrame extends JFrame {
    private final FlightAvailabilityDao availabilityDao = new FlightAvailabilityDao();
    private final BookingService bookingService = new BookingService();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Instance", "Flight", "Route", "Departure", "Aircraft", "Capacity", "Booked", "Available"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable flightTable = new JTable(tableModel);
    private final JTextField passengerIdField = new JTextField();
    private final JTextField bookingReferenceField = new JTextField();
    private final JTextField seatNumberField = new JTextField();
    private final JTextField cabinClassField = new JTextField("ECONOMY");
    private final JTextField ticketPriceField = new JTextField();
    private int selectedFlightInstanceId;

    public BookingFrame() {
        setTitle("Airport Management System - Direct Flight Booking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);

        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && flightTable.getSelectedRow() >= 0) {
                selectedFlightInstanceId = ((Number) tableModel.getValueAt(flightTable.getSelectedRow(), 0)).intValue();
            }
        });

        add(createFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(flightTable), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        loadFlightAvailability();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        formPanel.add(new JLabel("Passenger ID *"));
        formPanel.add(passengerIdField);
        formPanel.add(new JLabel("Booking reference * (8 characters)"));
        formPanel.add(bookingReferenceField);
        formPanel.add(new JLabel("Seat number *"));
        formPanel.add(seatNumberField);
        formPanel.add(new JLabel("Cabin class *"));
        formPanel.add(cabinClassField);
        formPanel.add(new JLabel("Ticket price *"));
        formPanel.add(ticketPriceField);
        formPanel.add(new JLabel("Flight selection"));
        formPanel.add(new JLabel("Select one row in the table below."));
        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        JButton bookButton = new JButton("Create Booking");
        bookButton.addActionListener(event -> createBooking());
        JButton refreshButton = new JButton("Refresh Flights");
        refreshButton.addActionListener(event -> loadFlightAvailability());
        buttonPanel.add(bookButton);
        buttonPanel.add(refreshButton);
        return buttonPanel;
    }

    private void loadFlightAvailability() {
        try {
            List<FlightAvailability> flights = availabilityDao.findAll();
            tableModel.setRowCount(0);
            for (FlightAvailability flight : flights) {
                tableModel.addRow(new Object[]{
                        flight.getInstanceId(), flight.getFlightCode(), flight.getRoute(),
                        flight.getDepartureTime(), flight.getAircraftModel(), flight.getCapacity(),
                        flight.getBookedSeats(), flight.getAvailableSeats()
                });
            }
        } catch (Exception exception) {
            showError("Unable to load flight availability.", exception);
        }
    }

    private void createBooking() {
        try {
            int passengerId = Integer.parseInt(passengerIdField.getText().trim());
            BigDecimal ticketPrice = new BigDecimal(ticketPriceField.getText().trim());
            int ticketId = bookingService.createDirectBooking(
                    passengerId, selectedFlightInstanceId, bookingReferenceField.getText(),
                    seatNumberField.getText(), cabinClassField.getText(), ticketPrice
            );
            loadFlightAvailability();
            JOptionPane.showMessageDialog(this, "Booking created successfully. Ticket ID: " + ticketId);
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Passenger ID must be a whole number and price must be numeric.",
                    "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception exception) {
            showError("Booking failed. No partial ticket was saved.", exception);
        }
    }

    private void showError(String message, Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, message, "Database error", JOptionPane.ERROR_MESSAGE);
    }
}
