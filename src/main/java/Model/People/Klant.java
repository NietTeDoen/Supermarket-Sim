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
    private List<List<Node>> routeSegments;
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

    private boolean hasCheckedOut = false;
    private static boolean bijKassa = false;

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

    private void startAction(String text, int waitTicks) {
        busy = true;
        actionTicks = waitTicks;
        maxActionTicks = waitTicks;
        currentActionText = text;
    }

    public void takeProduct(String productName, int waitTicks) {
        if (productName == null){
            throw new NullPointerException("Product name is null");
        }
        productsList.add(new Product(productName, 0.0));
        startAction("Pakt " + productName + "...", waitTicks);
    }

    @Override
    public void update() {
        if (busy) {
            if (currentActionText.equals("Wachten in de rij...")) {
                if (!bijKassa) {
                    // Kassa is vrij, queue actie mag stoppen
                    actionTicks--;
                }
                // anders blijven ze wachten, dus geen actionTicks--
            } else {
                // Alles buiten de queue telt gewoon ticks af
                actionTicks--;
            }

            if (actionTicks <= 0 && currentActionText != "Afrekenen...") {
                bijKassa = false;
            }
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

        // ✅ Kassalogica direct uitvoeren zodra klant kassa bereikt
        if (target.name.equalsIgnoreCase("cashier") && !hasCheckedOut) {
            handleSegmentComplete("cashier");
            hasCheckedOut = true;
            return; // Stop hier zodat hij niet direct doorloopt
        }

        if (distance < speed) {
            positie[0] = targetX;
            positie[1] = targetY;
            pathIndex++;
        } else {
            positie[0] += (dx / distance) * speed;
            positie[1] += (dy / distance) * speed;
        }
    }

    /** Acties op basis van node-naam i.p.v. segment index */
    private void handleSegmentComplete(String nodeName) {
        if (nodeName == null) return;

        switch (nodeName.toLowerCase()) {

            case "kast1" -> takeProduct("Appel", 30);
            case "kast2" -> takeProduct("Brood", 20);
            case "kast3" -> takeProduct("Melk", 25);
            case "liggend-kast-1" -> takeProduct("Chips", 40);
            case "liggend-kast-2" -> takeProduct("Koekjes", 35);
            case "koelkast" -> takeProduct("Yoghurt", 45);

            case "queue1", "queue2", "queue3" ->{
                if(bijKassa) {
                    startAction("Wachten in de rij...", 40);
                }
            }

            case "cashier" -> {
                bijKassa = true;
                startAction("Afrekenen...", 120);
            }
            case "exit" -> {
                System.out.println("Klant verlaat de winkel");
                bijKassa = false;
                Despawncharacter();
            }

            case "entrance", "pad1", "pad2" -> {
                // Geen actie nodig
            }

            default ->
                    System.out.println("Onbekende locatie: " + nodeName);
        }
    }

    public void draw(Graphics2D g) {
        // Tekent klant zelf
        if (image != null)
            g.drawImage(image, positie[0], positie[1], width, height, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(positie[0], positie[1], width, height);
        }

        // --- Mandje altijd boven hoofd ---
        if (!productsList.isEmpty()) {
            StringBuilder inventoryText = new StringBuilder("🛒 Mandje: ");
            for (int i = 0; i < productsList.size(); i++) {
                inventoryText.append(productsList.get(i).getNaam());
                if (i < productsList.size() - 1)
                    inventoryText.append(", ");
            }

            g.setFont(new Font("Arial", Font.BOLD, 13));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(inventoryText.toString());
            int textHeight = fm.getHeight();

            int textX = positie[0] + width / 2 - textWidth / 2;
            int textY = positie[1] - 10; // net boven het hoofd

            // Achtergrond (half-transparant zwart)
            int padding = 6;
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRoundRect(textX - padding, textY - textHeight + 3,
                    textWidth + padding * 2, textHeight, 10, 10);

            // Tekst mandje
            g.setColor(new Color(255, 230, 120));
            g.drawString(inventoryText.toString(), textX, textY);
        }

        // --- Laadbalk iets hoger, zodat mandje eronder blijft ---
        if (busy && maxActionTicks > 0) {
            int barWidth = 80;
            int barHeight = 10;
            int barX = positie[0] + width / 2 - barWidth / 2;
            int barY = positie[1] - 35; // hoger dan mandje

            float progress = 1f - ((float) actionTicks / maxActionTicks);

            // Achtergrond balk
            g.setColor(Color.DARK_GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);

            // Voortgang balk
            g.setColor(new Color(80, 200, 120));
            g.fillRect(barX, barY, (int) (barWidth * progress), barHeight);

            // Tekst boven de balk
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
