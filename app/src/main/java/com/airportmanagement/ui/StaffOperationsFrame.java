package com.airportmanagement.ui;

import com.airportmanagement.dao.FlightAvailabilityDao;
import com.airportmanagement.dao.FlightStaffAssignmentDao;
import com.airportmanagement.dao.StaffDao;
import com.airportmanagement.model.Staff;
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
import java.util.List;

public class StaffOperationsFrame extends JFrame {
    private final StaffDao staffDao = new StaffDao();
    private final FlightAvailabilityDao flightAvailabilityDao = new FlightAvailabilityDao();
    private final FlightStaffAssignmentDao assignmentDao = new FlightStaffAssignmentDao();
    private final DefaultTableModel staffModel = new DefaultTableModel(
            new String[]{"Staff ID", "First Name", "Last Name", "Email", "Phone", "Job Title"}, 0
    ) { public boolean isCellEditable(int row, int column) { return false; } };
    private final DefaultTableModel flightModel = new DefaultTableModel(
            new String[]{"Instance ID", "Flight", "Route", "Departure"}, 0
    ) { public boolean isCellEditable(int row, int column) { return false; } };
    private final JTable staffTable = new JTable(staffModel);
    private final JTable flightTable = new JTable(flightModel);
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField jobTitleField = new JTextField();
    private final JTextField dutyField = new JTextField();
    private int selectedStaffId;
    private int selectedFlightInstanceId;

    public StaffOperationsFrame() {
        setTitle("Airport Management System - Staff Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        staffTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && staffTable.getSelectedRow() >= 0) {
                selectedStaffId = ((Number) staffModel.getValueAt(staffTable.getSelectedRow(), 0)).intValue();
            }
        });
        flightTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && flightTable.getSelectedRow() >= 0) {
                selectedFlightInstanceId = ((Number) flightModel.getValueAt(flightTable.getSelectedRow(), 0)).intValue();
            }
        });
        add(createInputPanel(), BorderLayout.NORTH);
        add(createTablesPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.add(new JLabel("First name *")); panel.add(firstNameField);
        panel.add(new JLabel("Last name *")); panel.add(lastNameField);
        panel.add(new JLabel("Email *")); panel.add(emailField);
        panel.add(new JLabel("Phone")); panel.add(phoneField);
        panel.add(new JLabel("Job title *")); panel.add(jobTitleField);
        panel.add(new JLabel("Flight duty * (for assignment)")); panel.add(dutyField);
        return panel;
    }

    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        panel.add(new JScrollPane(staffTable));
        panel.add(new JScrollPane(flightTable));
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        JButton add = new JButton("Add Staff Member");
        add.addActionListener(event -> addStaff());
        JButton assign = new JButton("Assign Selected Staff to Selected Flight");
        assign.addActionListener(event -> assignStaff());
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(event -> refreshData());
        panel.add(add); panel.add(assign); panel.add(refresh);
        return panel;
    }

    private void refreshData() {
        try {
            staffModel.setRowCount(0);
            for (Staff staff : staffDao.findAll()) {
                staffModel.addRow(new Object[]{staff.getStaffId(), staff.getFirstName(), staff.getLastName(),
                        staff.getEmail(), staff.getPhone(), staff.getJobTitle()});
            }
            flightModel.setRowCount(0);
            for (FlightAvailability flight : flightAvailabilityDao.findAll()) {
                flightModel.addRow(new Object[]{flight.getInstanceId(), flight.getFlightCode(),
                        flight.getRoute(), flight.getDepartureTime()});
            }
        } catch (Exception exception) {
            showError("Unable to load staff operations data.", exception);
        }
    }

    private void addStaff() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String jobTitle = jobTitleField.getText().trim();
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || jobTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name, last name, email, and job title are required.", "Validation error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = staffDao.create(new Staff(0, firstName, lastName, email, phoneField.getText(), jobTitle));
            refreshData();
            JOptionPane.showMessageDialog(this, "Staff member added with ID " + id + ".");
        } catch (Exception exception) {
            showError("Unable to add staff. Email must be unique.", exception);
        }
    }

    private void assignStaff() {
        if (selectedStaffId == 0 || selectedFlightInstanceId == 0 || dutyField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select one staff row, one flight row, and enter a duty.", "Selection required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            assignmentDao.assign(selectedStaffId, selectedFlightInstanceId, dutyField.getText());
            JOptionPane.showMessageDialog(this, "Staff member assigned to the flight.");
        } catch (Exception exception) {
            showError("Unable to assign staff. This staff member may already be assigned to that flight.", exception);
        }
    }

    private void showError(String message, Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, message, "Database error", JOptionPane.ERROR_MESSAGE);
    }
}
