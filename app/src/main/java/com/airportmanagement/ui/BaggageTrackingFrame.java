package com.airportmanagement.ui;

import com.airportmanagement.dao.BaggageTrackingDao;
import com.airportmanagement.view.BaggageTrackingRecord;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

public class BaggageTrackingFrame extends JFrame {
    private final BaggageTrackingDao baggageTrackingDao = new BaggageTrackingDao();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Tracking ID", "Bag Tag", "Booking", "Leg", "Flight", "Route", "Departure", "Status", "Loaded At", "Unloaded At"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable baggageTable = new JTable(tableModel);
    private final JComboBox<String> statusBox = new JComboBox<>(
            new String[]{"PENDING", "LOADED", "UNLOADED", "MISSING"}
    );
    private int selectedBaggageSegmentId;

    public BaggageTrackingFrame() {
        setTitle("Airport Management System - Baggage Tracking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);

        baggageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        baggageTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && baggageTable.getSelectedRow() >= 0) {
                int row = baggageTable.getSelectedRow();
                selectedBaggageSegmentId = ((Number) tableModel.getValueAt(row, 0)).intValue();
                statusBox.setSelectedItem(tableModel.getValueAt(row, 7));
            }
        });

        JLabel explanation = new JLabel(
                "Each row is one physical bag on one flight leg. Select a row, then record its scan status."
        );
        explanation.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(explanation, BorderLayout.NORTH);
        add(new JScrollPane(baggageTable), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        loadBaggageTracking();
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        panel.add(new JLabel("New status:"));
        panel.add(statusBox);

        JButton updateButton = new JButton("Record Baggage Scan");
        updateButton.addActionListener(event -> updateStatus());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(event -> loadBaggageTracking());
        panel.add(updateButton);
        panel.add(refreshButton);
        return panel;
    }

    private void loadBaggageTracking() {
        try {
            List<BaggageTrackingRecord> records = baggageTrackingDao.findAll();
            tableModel.setRowCount(0);
            for (BaggageTrackingRecord record : records) {
                tableModel.addRow(new Object[]{
                        record.getBaggageSegmentId(), record.getTagNumber(), record.getBookingReference(),
                        record.getSegmentNumber(), record.getFlightCode(), record.getRoute(),
                        record.getDepartureTime(), record.getLoadStatus(), record.getLoadedAt(), record.getUnloadedAt()
                });
            }
        } catch (Exception exception) {
            showError("Unable to load baggage tracking data.", exception);
        }
    }

    private void updateStatus() {
        if (selectedBaggageSegmentId == 0) {
            JOptionPane.showMessageDialog(this, "Select a baggage row first.", "Selection required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            baggageTrackingDao.updateLoadStatus(selectedBaggageSegmentId, (String) statusBox.getSelectedItem());
            loadBaggageTracking();
            JOptionPane.showMessageDialog(this, "Baggage scan recorded.");
        } catch (Exception exception) {
            showError("Unable to record the baggage scan.", exception);
        }
    }

    private void showError(String message, Exception exception) {
        exception.printStackTrace();
        JOptionPane.showMessageDialog(this, message, "Database error", JOptionPane.ERROR_MESSAGE);
    }
}
