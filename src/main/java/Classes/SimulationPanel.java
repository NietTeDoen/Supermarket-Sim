package Classes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SimulationPanel extends JPanel {

    InputStream background = getClass().getResourceAsStream("/images/background.png");
    final BufferedImage image = ImageIO.read(background);

    public SimulationPanel() throws IOException {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawImage(image, 0, 0, this);
        g.fillRect(100,100,50,50);
    }
}
