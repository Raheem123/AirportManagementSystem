package com.airportmanagement.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        setTitle("Airport Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 370);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Airport Management System", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 12, 12));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 40, 80));

        JButton passengersButton = new JButton("Manage Passengers");
        passengersButton.addActionListener(event -> new PassengerFrame().setVisible(true));

        JButton bookingButton = new JButton("View Flights and Create Booking");
        bookingButton.addActionListener(event -> new BookingFrame().setVisible(true));

        JButton baggageButton = new JButton("Track Baggage by Flight Leg");
        baggageButton.addActionListener(event -> new BaggageTrackingFrame().setVisible(true));

        menuPanel.add(passengersButton);
        menuPanel.add(bookingButton);
        menuPanel.add(baggageButton);
        add(menuPanel, BorderLayout.CENTER);
    }
}
