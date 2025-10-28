package Model.People;

import Controller.TickController;
import Model.Logic.Node;
import Model.Store.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klant beweegt door de winkel volgens een pad van nodes en kan producten pakken.
 */
public class Klant extends Person {
    private List<Product> productsList = new ArrayList<>(); // producten die de klant pakt
    private List<Node> path;  // het pad dat deze klant volgt
    private int pathIndex = 0; // node in pad waar we naartoe bewegen
    private float speed = 5f;  // pixels per tick

    private int width = 100;   // sprite breedte
    private int height = 150;  // sprite hoogte
    protected int[] positie;

    private Image image;

    /**
     * Constructor
     * @param positie startpositie van de klant (x, y in pixels)
     * @param path pad van nodes die de klant volgt
     * @param sprite sprite naam
     */
    public Klant(int[] positie, List<Node> path, String sprite) {
        super(positie, path, sprite);
        this.positie = positie; // <-- belangrijk, anders blijft het null
        this.path = path;
        loadImage();
    }


    private void loadImage() {
        try {
            // Zorg dat "Klant.png" in src/main/resources/images/ zit
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/Klant.png"));
            image = icon.getImage();
        } catch (NullPointerException e) {
            System.out.println("Image not found! Zorg dat het pad klopt.");
            image = null;
        }
    }

    private boolean busy = false;   // true als de klant een product pakt
    private int actionTicks = 0;    // hoeveel ticks de actie duurt

    /**
     * Voeg een product toe aan de klant
     */
    public void takeProduct(String productName, int durationTicks) {
        productsList.add(new Product(productName, 0.00));
        busy = true;
        actionTicks = durationTicks; // bv. 30 ticks = 1 seconde bij 30fps
    }


    /**
     * Update functie die iedere tick wordt aangeroepen
     */
    @Override
    public void update() {
        // Als klant bezig is met een actie, wacht
        if (busy) {
            actionTicks--;
            if (actionTicks <= 0) busy = false;
            return; // stop update totdat actie klaar is
        }

        // Despawnen als pad klaar is
        if (path == null || pathIndex >= path.size()) {
            Despawncharacter();
            return;
        }

        Node target = path.get(pathIndex);
        int targetX = (int) (target.x * TickController.getPanelWidth()) - width / 2;
        int targetY = (int) (target.y * TickController.getPanelHeight()) - height;

        float dx = targetX - positie[0];
        float dy = targetY - positie[1];
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            // node bereikt
            positie[0] = targetX;
            positie[1] = targetY;
            pathIndex++;

            // hier kun je checken of node een schap is en product pakken
            // bv. takeProduct("Appel", 30);
        } else {
            // beweeg richting node
            positie[0] += (dx / distance) * speed;
            positie[1] += (dy / distance) * speed;
        }
    }


    /**
     * Getter voor productenlijst
     */
    public List<Product> getProductsList() {
        return productsList;
    }

    /**
     * Getter voor huidige node index
     */
    public int getPathIndex() {
        return pathIndex;
    }
}
