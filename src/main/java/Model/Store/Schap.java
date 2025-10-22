package Model.Store;

import View.SimulationPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * De {@code Schap}-klasse stelt een winkelrek (schap) voor in de supermarkt.
 * <p>
 * Elk schap heeft een specifieke positie op de map, een bepaald type
 * en een voorraad van producten. Ook wordt er een afbeelding geladen die het schap visueel
 * weergeeft in de simulatie.
 * </p>
 */
public class Schap {

    /** X-coördinaat van het schap op het scherm. */
    private double x;

    /** Y-coördinaat van het schap op het scherm. */
    private double y;

    /** Het type schap, dat bepaalt welke afbeelding en afmetingen worden gebruikt. */
    private SchapType type;

    /** De afbeelding die visueel bij dit schap hoort. */
    private BufferedImage image;

    /** De voorraad van producten in dit schap. */
    private Map<Product, Integer> voorraad;

    private static JPanel panel;

    public static void setPanel(JPanel p) {
        panel = p;
    }

    /**
     * Maakt een nieuw {@code Schap}-object aan met een positie en type.
     * <p>
     * De constructor initialiseert een lege voorraad en laadt automatisch
     * de bijbehorende afbeelding voor het opgegeven {@link SchapType}.
     * </p>
     *
     * @param x    de X-coördinaat van het schap
     * @param y    de Y-coördinaat van het schap
     * @param type het type schap dat bepaalt welke afbeelding wordt gebruikt
     */
    public Schap(double x, double y, SchapType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.voorraad = new HashMap<>();
        loadImage();
    }

    /**
     * Laadt de afbeelding die bij het {@link SchapType} hoort.
     * Als de afbeelding niet gevonden kan worden, wordt er een waarschuwing weergegeven
     * en blijft het {@code image}-veld op {@code null}.
     */
    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResource(type.getImagePath()));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Kon afbeelding niet laden voor schaptype: " + type);
            image = null;
        }
    }

    /**
     * Voegt een product toe aan de voorraad van dit schap.
     * Als het product al aanwezig is, wordt de hoeveelheid verhoogd.
     *
     * @param product  het {@link Product} dat moet worden toegevoegd
     * @param quantity het aantal eenheden dat moet worden toegevoegd
     */
    public void addProduct(Product product, int quantity) {
        voorraad.put(product, voorraad.getOrDefault(product, 0) + quantity);
    }

    /**
     * Verwijdert één eenheid van een product uit de voorraad.
     * <p>
     * Als de voorraad op 0 staat of het product niet voorkomt, gebeurt er niets.
     * </p>
     *
     * @param product het {@link Product} dat verwijderd moet worden
     * @return {@code true} als het product succesvol verwijderd is, anders {@code false}
     */
    public boolean removeProduct(Product product) {
        if (voorraad.containsKey(product) && voorraad.get(product) > 0) {
            voorraad.put(product, voorraad.get(product) - 1);
            return true;
        }
        return false;
    }

    /**
     * Tekent het schap op het scherm.
     * <p>
     * Als de afbeelding niet beschikbaar is, wordt een grijs vlak met de schapnaam getekend.
     * </p>
     *
     * @param g het {@link Graphics}-object waarmee het schap wordt getekend
     */
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int)(x * panel.getWidth()), (int)(y * panel.getHeight()), (int)(type.getWidth() * panel.getWidth()) , (int)(type.getHeight() * panel.getHeight()), null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect((int)x, (int)y, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString(type.name(), (int)x + 5, (int)y + 25);
        }
    }

    /**
     * Geeft de X-coördinaat van het schap.
     *
     * @return de X-coördinaat
     */
    public int getX() {
        return (int)x;
    }

    /**
     * Geeft de Y-coördinaat van het schap.
     *
     * @return de Y-coördinaat
     */
    public int getY() {
        return (int)y;
    }

    /**
     * Geeft het type schap.
     *
     * @return het {@link SchapType} van dit schap
     */
    public SchapType getType() {
        return type;
    }
}
