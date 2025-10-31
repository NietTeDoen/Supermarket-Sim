package Model.Store;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Kassa {

    private List<Product> scannedItems;
    private Double prijs;
    private BufferedImage image;
    private double x;
    private double y;

    // Sprite van de medewerker die bij de kassa staat
    private BufferedImage medewerkerImage;

    private static JPanel panel;

    public static void setPanel(JPanel p) {
        panel = p;
    }

    public Kassa(double x, double y) {
        this.x = x;
        this.y = y;
        this.scannedItems = new ArrayList<>();
        this.prijs = 0.0;
        loadImage();
        loadMedewerkerImage();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource("/images/Kassa.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Kon kassa-afbeelding niet laden!");
            image = null;
        }
    }

    private void loadMedewerkerImage() {
        try {
            medewerkerImage = ImageIO.read(getClass().getResource("/images/Cashier.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Kon medewerker-afbeelding niet laden!");
            medewerkerImage = null;
        }
    }

    public void draw(Graphics g) {
        int kX = (int)(x * panel.getWidth());
        int kY = (int)(y * panel.getHeight());
        int kW = (int)(0.1203 * panel.getWidth());
        int kH = (int)(0.3541 * panel.getHeight());

        // Kassa tekenen
        if (image != null) {
            g.drawImage(image, kX, kY, kW, kH, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(kX, kY, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("Kassa", kX + 5, kY + 25);
        }

        // Medewerker in het midden van de kassa tekenen
        if (medewerkerImage != null) {
            int mW = 100; // breedte medewerker
            int mH = 150; // hoogte medewerker
            int mX = kX + kW / 2 - mW / 2;
            double mY = kY + kH / 2.5 - mH / 2.5;
            g.drawImage(medewerkerImage, mX, (int) mY, mW, mH, null);
        }
    }

    public Double getPrijs() { return prijs; }
    public List<Product> getScannedItems() { return scannedItems; }

    public Boolean addProduct(Product product) {
        prijs += product.getPrijs();
        return scannedItems.add(product);
    }

    public Boolean removeProduct(Product product) {
        prijs -= product.getPrijs();
        return scannedItems.remove(product);
    }
}
