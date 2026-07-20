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
        setSize(480, 300);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Airport Management System", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(2, 1, 12, 12));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 40, 80));

        JButton passengersButton = new JButton("Manage Passengers");
        passengersButton.addActionListener(event -> new PassengerFrame().setVisible(true));

        JButton bookingButton = new JButton("View Flights and Create Booking");
        bookingButton.addActionListener(event -> new BookingFrame().setVisible(true));

        menuPanel.add(passengersButton);
        menuPanel.add(bookingButton);
        add(menuPanel, BorderLayout.CENTER);
    }
}
