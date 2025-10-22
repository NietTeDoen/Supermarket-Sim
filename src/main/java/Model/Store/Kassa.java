package Model.Store;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * De {@code Kassa}-klasse stelt een kassa in de supermarkt voor.
 * <p>
 * Deze klasse bevat zowel logische functionaliteit (zoals het scannen en
 * verwijderen van producten) als visuele eigenschappen voor weergave
 * in de simulatie.
 * </p>
 */
public class Kassa {

    /** Lijst met alle gescande producten aan deze kassa. */
    private List<Product> scannedItems;

    /** De totale prijs van alle gescande producten. */
    private Double prijs;

    /** Afbeelding die de kassa visueel weergeeft. */
    private BufferedImage image;

    /** X-coördinaat van de kassa op het scherm. */
    private double x;

    /** Y-coördinaat van de kassa op het scherm. */
    private double y;

    private static JPanel panel;

    public static void setPanel(JPanel p) {
        panel = p;
    }

    /**
     * Constructor voor logische initialisatie van een kassa.
     * Wordt gebruikt in niet-visuele context.
     *
     * @param scannedItems de lijst van gescande producten
     * @param prijs        de huidige totaalprijs
     */
    public Kassa(List<Product> scannedItems, Double prijs) {
        this.scannedItems = scannedItems;
        this.prijs = prijs;
    }

    /**
     * Constructor voor visuele weergave van de kassa in de simulatie.
     * <p>
     * Deze constructor initialiseert een lege kassa met standaardprijs 0
     * en laadt automatisch de kassa-afbeelding uit de resourcesmap.
     * </p>
     *
     * @param x de X-coördinaat waar de kassa getekend moet worden
     * @param y de Y-coördinaat waar de kassa getekend moet worden
     */
    public Kassa(double x, double y) {
        this.x = x;
        this.y = y;
        this.scannedItems = new ArrayList<>();
        this.prijs = 0.0;
        loadImage();
    }

    /**
     * Laadt de afbeelding van de kassa uit de resource-map.
     * Als de afbeelding niet gevonden kan worden, wordt een waarschuwing weergegeven.
     */
    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource("/images/Kassa.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Kon kassa-afbeelding niet laden!");
            image = null;
        }
    }

    /**
     * Tekent de kassa op het scherm.
     * <p>
     * Als de afbeelding niet beschikbaar is, wordt een rechthoek getekend.
     * </p>
     *
     * @param g het {@link Graphics}-object waarmee de kassa wordt getekend
     */
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int)(x * panel.getWidth()), (int)(y * panel.getHeight()), (int)(0.1203 * panel.getWidth()) , (int)(0.3541 * panel.getHeight()), null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect((int)x, (int)y, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("Kassa", (int)x + 5, (int)y + 25);
        }
    }

    /**
     * Geeft de totale prijs van alle gescande producten.
     *
     * @return de huidige totaalprijs
     */
    public Double getPrijs() {
        return prijs;
    }

    /**
     * Geeft de lijst van alle gescande producten.
     *
     * @return een lijst van {@link Product}-objecten
     */
    public List<Product> getScannedItems() {
        return scannedItems;
    }

    /**
     * Voegt een product toe aan de kassa en telt de prijs op bij het totaal.
     *
     * @param product het toe te voegen {@link Product}
     * @return {@code true} als het product succesvol is toegevoegd
     */
    public Boolean addProduct(Product product) {
        prijs += product.getPrijs();
        return scannedItems.add(product);
    }

    /**
     * Verwijdert een product uit de kassa en trekt de prijs af van het totaal.
     *
     * @param product het te verwijderen {@link Product}
     * @return {@code true} als het product succesvol is verwijderd
     */
    public Boolean removeProduct(Product product) {
        prijs -= product.getPrijs();
        return scannedItems.remove(product);
    }
}
