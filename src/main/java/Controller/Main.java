package Controller;

import View.SimulationPanel;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Starting Tick Controller...");
                TickController tickController = new TickController();
                SetupApplication();
                  new Thread(() -> {
                    try {
                        tickController.start();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void SetupApplication() throws IOException {
            JFrame frame = new JFrame("Supermarket Simulator");

            ImageIcon icon = new ImageIcon(Main.class.getResource("/images/Logo.png"));
            frame.setIconImage(icon.getImage());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);

            try {
                frame.add(new SimulationPanel());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

}
