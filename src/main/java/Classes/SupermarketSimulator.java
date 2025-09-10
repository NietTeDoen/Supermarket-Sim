package Classes;

import javax.swing.*;
import java.io.IOException;

public class SupermarketSimulator {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Supermarket Simulator");
        JButton button = new JButton("Simulate");
        frame.add(button);

        frame.add(new SimulationPanel());

        frame.setSize(1280,720);
        frame.setVisible(true);
    }
}
