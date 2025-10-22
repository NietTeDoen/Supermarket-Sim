package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import Controller.Main;
import Model.Logic.Node;
import Model.Logic.World;

public class SimulationPanel extends JPanel {

    private BufferedImage backgroundImage;
    private World world;

    public SimulationPanel() throws IOException {
        this.world = Main.world;

        InputStream background = getClass().getResourceAsStream("/images/background.png");
        if (background != null) {
            backgroundImage = ImageIO.read(background);
        } else {
            System.err.println("⚠️ Background image not found!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
        }

        // Nodes tekenen
        for (Map.Entry<String, Node> entry : Main.nodes.entrySet()) {
            Node n = entry.getValue();
            int tempx = (int) (n.x * panelWidth);
            int tempy = (int) (n.y * panelHeight);
            g.setColor(Color.RED);
            g.fillOval(tempx, tempy, 15, 15);
        }


        if (world != null) {
            world.draw(g);
        }
    }

    public int getPanelWidth() {
        return getWidth();
    }

    public int getPanelHeight() {
        return getHeight();
    }
}
