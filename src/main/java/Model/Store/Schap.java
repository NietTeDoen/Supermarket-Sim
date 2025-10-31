package Model.Store;

import Model.Logic.World; // ⬅ import toegevoegd om voorraad op te vragen
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Schap {
    private double x;
    private double y;
    private SchapType type;
    private BufferedImage image;
    private String productNaam;  // ⬅ toegevoegd
    private static JPanel panel;

    public static void setPanel(JPanel p) {
        panel = p;
    }

    public Schap(double x, double y, SchapType type, String product) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.productNaam = product;
        loadImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource(type.getImagePath()));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Kon afbeelding niet laden voor schaptype: " + type);
            image = null;
        }
    }

    public void draw(Graphics g) {
        if (panel == null) return;
        int px = (int) (x * panel.getWidth());
        int py = (int) (y * panel.getHeight());
        int w = (int) (type.getWidth() * panel.getWidth());
        int h = (int) (type.getHeight() * panel.getHeight());

        // Schap zelf tekenen
        if (image != null) {
            g.drawImage(image, px, py, w, h, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(px, py, w, h);
            g.setColor(Color.BLACK);
            g.drawString(type.name(), px + 5, py + h / 2);
        }

        // 🔽 Nieuw gedeelte: voorraadlabel tekenen erboven
        int voorraad = World.getVoorraad(productNaam);

        if (voorraad < 5) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 14));

            String tekst;
            Color textColor;
            Color bgColor;

            if (voorraad == 0) {
                tekst = productNaam + " - OUT OF STOCK";
                textColor = Color.RED;
                bgColor = new Color(40, 0, 0, 200); // donkerrood met transparantie
            } else {
                tekst = productNaam + " - LOW STOCK (" + voorraad + ")";
                textColor = Color.YELLOW;
                bgColor = new Color(50, 50, 0, 200); // donkergeel
            }

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(tekst);
            int textHeight = fm.getHeight();
            int padding = 4;

            // positie label: gecentreerd boven het schap
            int labelX = px + w / 2 - textWidth / 2 - padding;
            int labelY = py - textHeight - 5;

            // donkere balk tekenen
            g2d.setColor(bgColor);
            g2d.fillRoundRect(labelX, labelY, textWidth + padding * 2, textHeight + 4, 8, 8);

            // tekst tekenen
            g2d.setColor(textColor);
            g2d.drawString(tekst, labelX + padding, labelY + textHeight);
        }
    }

    public String getProductNaam() {
        return productNaam;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public SchapType getType() {
        return type;
    }
}
