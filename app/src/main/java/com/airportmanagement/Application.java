package com.airportmanagement;

import com.airportmanagement.ui.PassengerFrame;

import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PassengerFrame().setVisible(true));
    }
}
