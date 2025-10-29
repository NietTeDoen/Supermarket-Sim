package Model.People;

import Controller.TickController;
import Model.Logic.Node;
import Model.Store.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Klant extends Person {
    private List<Product> productsList = new ArrayList<>();
    private List<List<Node>> routeSegments; // segmenten van zijn route
    private int currentSegmentIndex = 0;
    private int pathIndex = 0;
    private float speed = 5f;

    private boolean busy = false;
    private int actionTicks = 0;
    private int maxActionTicks = 0;
    private String currentActionText = "";

    private int width = 100;
    private int height = 150;
    protected int[] positie;
    private Image image;

    public Klant(int[] positie, List<List<Node>> routeSegments, String sprite) {
        super(positie, null, sprite);
        this.positie = positie;
        this.routeSegments = routeSegments;
        loadImage();
    }

    private void loadImage() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/Klant.png"));
            image = icon.getImage();
        } catch (NullPointerException e) {
            System.out.println("Image not found! Zorg dat het pad klopt.");
            image = null;
        }
    }

    /** Universele actie met laadbalk */
    private void startAction(String text, int waitTicks) {
        busy = true;
        actionTicks = waitTicks;
        maxActionTicks = waitTicks;
        currentActionText = text;
    }

    /** Pak een product */
    public void takeProduct(String productName, int waitTicks) {
        productsList.add(new Product(productName, 0.0));
        startAction("Pakt " + productName + "...", waitTicks);
    }

    @Override
    public void update() {
        // Als bezig: alleen actionTicks verminderen, geen beweging
        if (busy) {
            actionTicks--;
            if (actionTicks <= 0) {
                busy = false;
                currentActionText = "";
            }
            return;
        }

        // Alle segmenten afgerond?
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

        // Segment afgerond?
        if (pathIndex >= currentPath.size()) {
            handleSegmentComplete(currentSegmentIndex);
            currentSegmentIndex++;
            pathIndex = 0;
            return;
        }

        // Beweeg naar target node
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

    /** Acties na elk segment */
    private void handleSegmentComplete(int segmentIndex) {
        switch (segmentIndex) {
            case 0 -> takeProduct("Appel", 30);
            case 1 -> takeProduct("Brood", 20);
            case 2 -> startAction("Afrekenen...", 50);
            default -> System.out.println("Klant verlaat de winkel");
        }
    }

    /** Tekent klant + laadbalk */
    public void draw(Graphics2D g) {
        if (image != null)
            g.drawImage(image, positie[0], positie[1], width, height, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(positie[0], positie[1], width, height);
        }

        // Laadbalk tekenen als bezig
        if (busy && maxActionTicks > 0) {
            int barWidth = 80;
            int barHeight = 10;
            int barX = positie[0] + width / 2 - barWidth / 2;
            int barY = positie[1] - 20;

            float progress = 1f - ((float) actionTicks / maxActionTicks);

            g.setColor(Color.DARK_GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);
            g.setColor(new Color(80, 200, 120));
            g.fillRect(barX, barY, (int) (barWidth * progress), barHeight);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.drawString(currentActionText, barX, barY - 5);
        }
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    public int getCurrentSegmentIndex() {
        return currentSegmentIndex;
    }
}
