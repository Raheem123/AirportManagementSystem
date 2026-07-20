package com.airportmanagement.ui;

import com.airportmanagement.dao.PassengerDao;
import com.airportmanagement.model.Passenger;

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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PassengerFrame extends JFrame {
    private final PassengerDao passengerDao = new PassengerDao();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "First Name", "Last Name", "Date of Birth", "Email", "Phone", "Passport"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable passengerTable = new JTable(tableModel);
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField dateOfBirthField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField passportField = new JTextField();

    private int selectedPassengerId;

    public PassengerFrame() {
        setTitle("Airport Management System - Passengers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 650);
        setLocationRelativeTo(null);

        passengerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        passengerTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                populateFormFromSelection();
            }
        });

        add(createFormPanel(), BorderLayout.NORTH);
        add(new JScrollPane(passengerTable), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadPassengers();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        formPanel.add(new JLabel("First name *"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last name *"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Date of birth * (yyyy-MM-dd)"));
        formPanel.add(dateOfBirthField);
        formPanel.add(new JLabel("Email"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Passport number"));
        formPanel.add(passportField);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(event -> addPassenger());

        JButton updateButton = new JButton("Update Selected");
        updateButton.addActionListener(event -> updatePassenger());

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(event -> deletePassenger());

        JButton clearButton = new JButton("Clear Form");
        clearButton.addActionListener(event -> clearForm());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(event -> loadPassengers());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void loadPassengers() {
        try {
            List<Passenger> passengers = passengerDao.findAll();
            tableModel.setRowCount(0);

            for (Passenger passenger : passengers) {
                tableModel.addRow(new Object[]{
                        passenger.getPassengerId(),
                        passenger.getFirstName(),
                        passenger.getLastName(),
                        passenger.getDateOfBirth(),
                        passenger.getEmail(),
                        passenger.getPhone(),
                        passenger.getPassportNumber()
                });
            }
        } catch (Exception exception) {
            showError("Unable to load passengers.", exception);
        }
    }

    private void addPassenger() {
        try {
            Passenger passenger = readPassengerFromForm(0);
            int passengerId = passengerDao.create(passenger);
            loadPassengers();
            clearForm();
            JOptionPane.showMessageDialog(this, "Passenger added with ID " + passengerId + ".");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception exception) {
            showError("Unable to add passenger. Email and passport number must be unique.", exception);
        }
    }

    private void updatePassenger() {
        if (selectedPassengerId == 0) {
            JOptionPane.showMessageDialog(this, "Select a passenger to update.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Passenger passenger = readPassengerFromForm(selectedPassengerId);
            if (passengerDao.update(passenger)) {
                loadPassengers();
                clearForm();
                JOptionPane.showMessageDialog(this, "Passenger updated.");
            }
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Validation error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception exception) {
            showError("Unable to update passenger.", exception);
        }
    }

    private void deletePassenger() {
        if (selectedPassengerId == 0) {
            JOptionPane.showMessageDialog(this, "Select a passenger to delete.", "No selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete the selected passenger?",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (passengerDao.delete(selectedPassengerId)) {
                loadPassengers();
                clearForm();
                JOptionPane.showMessageDialog(this, "Passenger deleted.");
            }
        } catch (Exception exception) {
            showError("Unable to delete passenger. Existing tickets may reference this passenger.", exception);
        }
    }

    private Passenger readPassengerFromForm(int passengerId) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dateOfBirthText = dateOfBirthField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dateOfBirthText.isEmpty()) {
            throw new IllegalArgumentException("First name, last name, and date of birth are required.");
        }

        try {
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthText);
            return new Passenger(
                    passengerId,
                    firstName,
                    lastName,
                    dateOfBirth,
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    passportField.getText().trim()
            );
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Date of birth must use yyyy-MM-dd format.");
        }
    }

    private void populateFormFromSelection() {
        int selectedRow = passengerTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        selectedPassengerId = ((Number) tableModel.getValueAt(selectedRow, 0)).intValue();
        firstNameField.setText(valueAt(selectedRow, 1));
        lastNameField.setText(valueAt(selectedRow, 2));
        dateOfBirthField.setText(valueAt(selectedRow, 3));
        emailField.setText(valueAt(selectedRow, 4));
        phoneField.setText(valueAt(selectedRow, 5));
        passportField.setText(valueAt(selectedRow, 6));
    }

    private String valueAt(int row, int column) {
        Object value = tableModel.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private void clearForm() {
        selectedPassengerId = 0;
        passengerTable.clearSelection();
        firstNameField.setText("");
        lastNameField.setText("");
        dateOfBirthField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passportField.setText("");
    }

    private void showError(String message, Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, message, "Database error", JOptionPane.ERROR_MESSAGE);
    }
}
