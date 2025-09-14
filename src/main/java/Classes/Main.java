package Classes;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                CreateAndShowGUI();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void CreateAndShowGUI() throws IOException {
        JFrame frame = new JFrame("Supermarket Simulator");
        JButton button = new JButton("Simulate");
        frame.add(button);

        frame.add(new SimulationPanel());

        frame.setSize(1280,720);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
