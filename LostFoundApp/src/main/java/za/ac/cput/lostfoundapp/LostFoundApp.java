package za.ac.cput.lostfoundapp;

import za.ac.cput.lostfoundapp.gui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point - Launches FindMyItem System
 */
public class LostFoundApp {

    public static void main(String[] args) {
        // Use Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }// end of main
}// end of class
