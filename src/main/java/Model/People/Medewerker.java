package Model.People;

import Controller.TickController;
import Model.Logic.Node;
import Model.Logic.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Medewerker extends Person {

    private String taak;
    private float speed = 8f;
    private int width = 100;
    private int height = 150;

    private boolean busy = false;
    private int actionTicks = 0;
    private int maxActionTicks = 0;
    private String currentActionText = "";

    private List<List<Node>> routeSegments;
    private int currentSegmentIndex = 0;
    private int pathIndex = 0;

    private Node targetSchapNode;

    private Image image;

    public Medewerker(int[] positie, List<List<Node>> routeSegments, String sprite, String taak) {
        super(positie, null, sprite); // path van Person gebruiken we niet
        this.routeSegments = routeSegments;
        this.taak = taak;
        loadImage();
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

    public void setTargetSchapNode(Node target) {
        this.targetSchapNode = target;
    }

    private void startAction(String text, int waitTicks) {
        busy = true;
        actionTicks = waitTicks;
        maxActionTicks = waitTicks;
        currentActionText = text;
    }

    private void handleSegmentComplete(String nodeName) {
        switch (nodeName.toLowerCase()) {
            case "kast1" -> startRefill("Appel");
            case "kast2" -> startRefill("Brood");
            case "kast3" -> startRefill("Melk");
            case "liggend-kast-1" -> startRefill("Chips");
            case "liggend-kast-2" -> startRefill("Koekjes");
            case "koelkast" -> startRefill("Yoghurt");
            case "exit" -> Despawncharacter();
            default -> {} // niks doen
        }
    }

    private void startRefill(String product) {
        taak = product;
        busy = true;
        actionTicks = 100; // aantal ticks dat het bijvullen duurt
        World.setVoorraad(product, 7);
        startAction("Refilling the: " + taak, actionTicks);
    }

    @Override
    public void update() {
        if (busy) {
            // Medewerker is bezig met bijvullen
            actionTicks--;
            if (actionTicks <= 0) {
                busy = false;
                taak = null; // taak klaar
            }
            return; // stop met bewegen zolang busy
        }

        // Normale beweging door segmenten
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
        float distance = (float) Math.sqrt(dx*dx + dy*dy);

        if (distance < speed) {
            positie[0] = targetX;
            positie[1] = targetY;
            pathIndex++;
        } else {
            positie[0] += (dx / distance) * speed;
            positie[1] += (dy / distance) * speed;
        }
    }

    public String getCurrentTask() {
        return taak;
    }

    @Override
    public void draw(Graphics g) {
        // Tekenen medewerker
        if (image != null) {
            g.drawImage(image, positie[0], positie[1], width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(positie[0], positie[1], width, height);
        }

        // Laadbalk
        if (busy && maxActionTicks > 0) {
            int barWidth = 80;
            int barHeight = 10;
            int barX = positie[0] + width / 2 - barWidth / 2;
            int barY = positie[1] - 35;

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
}
