package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SimulationPanel extends JPanel {

    InputStream background = getClass().getResourceAsStream("/images/background.png");
    BufferedImage image = ImageIO.read(background);

    public SimulationPanel() throws IOException {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.drawImage(image, 0, 0, width, height, this);
    }
}
