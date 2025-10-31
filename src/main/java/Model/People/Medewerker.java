package Model.People;

import Controller.TickController;
import Model.Logic.Node;
import Model.Store.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Medewerker extends Person {
    private String Taak;

    /** Beweging snelheid van de medewerker */
    private float speed = 8f;

    /** Afmetingen van de klant (pixels) */
    private int width = 100;
    private int height = 150;

    /** Geeft aan of de klant momenteel een actie uitvoert */
    private boolean busy = false;

    /** Ticks die een actie nog moet duren */
    private int actionTicks = 0;

    /** Maximale ticks voor de huidige actie */
    private int maxActionTicks = 0;

    /** Beschrijving van de huidige actie */
    private String currentActionText = "";

    /** Huidige positie van de klant (x, y) */
    protected int[] positie;

    /** Sprite image van de klant */
    private Image image;

    /** Route van de klant, onderverdeeld in segmenten */
    private List<List<Node>> routeSegments;

    /** Index van het huidige segment van de route */
    private int currentSegmentIndex = 0;

    /** Index van de positie binnen het huidige segment */
    private int pathIndex = 0;

    public Medewerker(int[] positie, List<Node> target, String sprite, String Taak) {
        super(positie, target, sprite);
        this.Taak = Taak;
    }

    private void loadImage() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/medewerker.png"));
            image = icon.getImage();
        } catch (NullPointerException e) {
            System.out.println("Image not found! Zorg dat het pad klopt.");
            image = null;
        }
    }

    private void startAction(String text, int waitTicks) {
        busy = true;
        actionTicks = waitTicks;
        maxActionTicks = waitTicks;
        currentActionText = text;
    }

    @Override
    public void update() {
        if (busy) {
            if (currentActionText.equals("Restocking")) {
                actionTicks--;

                if (actionTicks <= 0) {
                    busy = false;
                    currentActionText = "";
                }
                return;
            }

            if (currentSegmentIndex >= routeSegments.size()) {
                Despawncharacter();
                return;
            }

            List<Node> currentPath = routeSegments.get(currentSegmentIndex);
            if (currentPath == null || currentPath.isEmpty()) {
                currentSegmentIndex++;
                pathIndex = 0;
                return;
            }

            if (pathIndex >= currentPath.size()) {
                Node last = currentPath.get(currentPath.size() - 1);
                handleSegmentComplete(last.name);
                currentSegmentIndex++;
                pathIndex = 0;
                return;
            }

            Node target = currentPath.get(pathIndex);
            int targetX = (int) (target.x * TickController.getPanelWidth()) - width / 2;
            int targetY = (int) (target.y * TickController.getPanelHeight()) - height;

            float dx = targetX - positie[0];
            float dy = targetY - positie[1];
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance < speed) {
                positie[0] = targetX;
                positie[1] = targetY;
                pathIndex++;
            } else {
                positie[0] += (dx / distance) * speed;
                positie[1] += (dy / distance) * speed;
            }
        }
    }

    private void RefillProduct(String productName, int waitTicks) {
        if (productName == null){
            throw new NullPointerException("Product name is null");
        }
        startAction("Refills " + productName + "...", waitTicks);
    }

    private void handleSegmentComplete(String nodeName) {
        if (nodeName == null) return;

        switch (nodeName.toLowerCase()) {
            case "kast1" -> RefillProduct("Appel", 100);
            case "kast2" -> RefillProduct("Brood", 100);
            case "kast3" -> RefillProduct("Melk", 100);
            case "liggend-kast-1" -> RefillProduct("Chips", 100);
            case "liggend-kast-2" -> RefillProduct("Koekjes", 100);
            case "koelkast" -> RefillProduct("Yoghurt", 100);
            case "exit" -> {
                System.out.println("Medewerker verlaat de winkel");
                Despawncharacter();
            }
            case "entrance", "pad1", "pad2" -> { /* Geen actie nodig */ }
            default -> System.out.println("Onbekende locatie: " + nodeName);
        }
    }
}
