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
import Model.People.Klant;

/**
 * JPanel waarin de simulatie wordt weergegeven.
 * <p>
 * Tekent de achtergrond, nodes, klanten en andere wereldobjecten.
 * </p>
 */
public class SimulationPanel extends JPanel {

    /** Achtergrondafbeelding van het winkelpaneel */
    private BufferedImage backgroundImage;

    /** De wereld die getekend wordt */
    private World world;

    /**
     * Constructor voor SimulationPanel.
     * <p>
     * Laadt de achtergrondafbeelding en initialiseert de wereld.
     * </p>
     *
     * @throws IOException indien de achtergrondafbeelding niet kan worden geladen
     */
    public SimulationPanel() throws IOException {
        this.world = Main.world;

        InputStream background = getClass().getResourceAsStream("/images/background.png");
        if (background != null) {
            backgroundImage = ImageIO.read(background);
        } else {
            System.err.println("⚠️ Background image not found!");
        }
    }

    /**
     * Tekent de componenten van het panel.
     * <p>
     * Tekent de achtergrond, nodes, wereldobjecten en alle klanten.
     * </p>
     *
     * @param g Graphics object gebruikt voor tekenen
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Achtergrond tekenen
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

        // Andere objecten uit de wereld tekenen
        if (world != null) {
            world.draw(g);
        }

        Graphics2D g2 = (Graphics2D) g;

        // Alle klanten tekenen
        for (Klant k : World.getPersons()) {
            k.draw(g2);
        }
    }

    /**
     * Verkrijg de breedte van het paneel.
     *
     * @return breedte in pixels
     */
    public int getPanelWidth() {
        return getWidth();
    }

    /**
     * Verkrijg de hoogte van het paneel.
     *
     * @return hoogte in pixels
     */
    public int getPanelHeight() {
        return getHeight();
    }
}
