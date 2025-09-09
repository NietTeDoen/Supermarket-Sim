package com.example.supermarketsim;

import javax.swing.*;
import java.awt.*;

public class SupermarketSimulator {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Supermarket Simulator");
        JButton button = new JButton("Simulate");
        frame.add(button);

        frame.add(new SimulationPanel());

        frame.setSize(400,400);
        frame.setVisible(true);
    }
}
