package com.airportmanagement;

import com.airportmanagement.ui.MainMenuFrame;

import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuFrame().setVisible(true));
    }
}
