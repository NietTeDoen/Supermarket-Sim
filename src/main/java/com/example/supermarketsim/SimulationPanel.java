package com.example.supermarketsim;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(100,100,50,50);
    }
}
