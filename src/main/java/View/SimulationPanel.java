package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import Controller.Main;
import Model.Logic.Node;

public class SimulationPanel extends JPanel {

    InputStream background = getClass().getResourceAsStream("/images/background.png");
    BufferedImage image = ImageIO.read(background);

    public int width  = image.getWidth();
    public int height = image.getHeight();

    public SimulationPanel() throws IOException {
    }
    public int[] getRes(){
        return new int[]{image.getWidth(), image.getHeight()};
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, this);

        for (var entry : Main.nodes.entrySet()) {
            String key = entry.getKey();
            Node n = entry.getValue();

            float tempx = n.x * width;
            float tempy = n.y * height;

            g.setColor(Color.RED);

            g.fillOval((int) tempx, (int) tempy, 15, 15);
        }
    }
}
