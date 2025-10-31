package Model.People;

import Controller.TickController;
import Model.Logic.Node;
import Model.Logic.World;
import Model.Store.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse die een klant in de winkel voorstelt.
 * <p>
 * Een klant kan producten pakken, in de rij wachten, naar de kassa gaan en uiteindelijk de winkel verlaten.
 * De klant beweegt langs een route die uit segmenten bestaat, en kan acties uitvoeren zoals "pakken" of "afrekenen".
 * Het mandje wordt altijd boven het hoofd van de klant weergegeven.
 * </p>
 */
public class Klant extends Person {

    /** Lijst met producten die de klant heeft gepakt */
    private List<Product> productsList = new ArrayList<>();

    /** Route van de klant, onderverdeeld in segmenten */
    private List<List<Node>> routeSegments;

    /** Index van het huidige segment van de route */
    private int currentSegmentIndex = 0;

    /** Index van de positie binnen het huidige segment */
    private int pathIndex = 0;

    /** Beweging snelheid van de klant */
    private float speed = 5f;

    /** Geeft aan of de klant momenteel een actie uitvoert */
    private boolean busy = false;

    /** Ticks die een actie nog moet duren */
    private int actionTicks = 0;

    /** Maximale ticks voor de huidige actie */
    private int maxActionTicks = 0;

    /** Beschrijving van de huidige actie */
    private String currentActionText = "";

    /** Afmetingen van de klant (pixels) */
    private int width = 100;
    private int height = 150;

    /** Huidige positie van de klant (x, y) */
    protected int[] positie;

    /** Sprite image van de klant */
    private Image image;

    /** Geeft aan of de klant al heeft afgerekend */
    private boolean hasCheckedOut = false;

    /** Geeft aan of er iemand bij de kassa is (gedeeld tussen alle klanten) */
    private static boolean bijKassa = false;

    /**
     * Maakt een nieuwe klant met een startpositie, route en sprite.
     *
     * @param positie      startpositie van de klant als array [x, y]
     * @param routeSegments lijst van route segmenten die de klant volgt
     * @param sprite       pad naar het sprite-bestand
     */
    public Klant(int[] positie, List<List<Node>> routeSegments, String sprite) {
        super(positie, null, sprite);
        this.positie = positie;
        this.routeSegments = routeSegments;
        loadImage();
    }

    /** Laadt de afbeelding voor de klant. */
    private void loadImage() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/Klant.png"));
            image = icon.getImage();
        } catch (NullPointerException e) {
            System.out.println("Image not found! Zorg dat het pad klopt.");
            image = null;
        }
    }

    /**
     * Start een actie voor de klant.
     *
     * @param text      beschrijving van de actie
     * @param waitTicks aantal ticks dat de actie duurt
     */
    private void startAction(String text, int waitTicks) {
        busy = true;
        actionTicks = waitTicks;
        maxActionTicks = waitTicks;
        currentActionText = text;
    }

    /**
     * Laat de klant een product pakken.
     *
     * @param productName naam van het product
     * @param waitTicks   aantal ticks dat het pakken duurt
     */
    public void takeProduct(String productName, int waitTicks) {
        if (productName == null){
            throw new NullPointerException("Product name is null");
        }
        productsList.add(new Product(productName, 0.0));
        startAction("Pakt " + productName + "...", waitTicks);
    }

    public void BeAngry(int waitTicks) {
        startAction("Customer is angry.", waitTicks);
    }

    /**
     * Update de status en positie van de klant.
     * <p>
     * Beweegt de klant langs de route, voert acties uit en behandelt de kassalogica.
     * </p>
     */
    @Override
    public void update() {
        if (busy) {
            if (currentActionText.equals("Wachten in de rij...")) {
                if (!bijKassa) {
                    actionTicks--;
                }
            } else {
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

        if (target.name.equalsIgnoreCase("cashier") && !hasCheckedOut) {
            handleSegmentComplete("cashier");
            hasCheckedOut = true;
            return;
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

    /**
     * Voert acties uit op basis van de voltooide node.
     *
     * @param nodeName naam van de node die voltooid is
     */
    private void handleSegmentComplete(String nodeName) {
        if (nodeName == null) return;

        switch (nodeName.toLowerCase()) {
            case "kast1" -> {
                if (World.getVoorraad("Appel") > 0) {
                    takeProduct("Appel", 30);
                    World.lowerVoorraadByOne("Appel");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Appels meer op voorraad!");
                }
            }
            case "kast2" -> {
                if (World.getVoorraad("Brood") > 0) {
                    takeProduct("Brood", 20);
                    World.lowerVoorraadByOne("Brood");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Brood meer op voorraad!");
                }
            }
            case "kast3" -> {
                if (World.getVoorraad("Melk") > 0) {
                    takeProduct("Melk", 25);
                    World.lowerVoorraadByOne("Melk");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Melk meer op voorraad!");
                }
            }
            case "liggend-kast-1" -> {
                if (World.getVoorraad("Chips") > 0) {
                    takeProduct("Chips", 40);
                    World.lowerVoorraadByOne("Chips");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Chips meer op voorraad!");
                }
            }
            case "liggend-kast-2" -> {
                if (World.getVoorraad("Koekjes") > 0) {
                    takeProduct("Koekjes", 35);
                    World.lowerVoorraadByOne("Koekjes");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Koekjes meer op voorraad!");
                }
            }
            case "koelkast" -> {
                if (World.getVoorraad("Yoghurt") > 0) {
                    takeProduct("Yoghurt", 45);
                    World.lowerVoorraadByOne("Yoghurt");
                } else {
                    BeAngry(50);
                    System.out.println("⚠️ Geen Yoghurt meer op voorraad!");
                }
            }
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
            case "entrance", "pad1", "pad2" -> { /* Geen actie nodig */ }
            default -> System.out.println("Onbekende locatie: " + nodeName);
        }
    }

    /**
     * Tekent de klant, mandje boven het hoofd en eventuele laadbalk.
     *
     * @param g Graphics2D object om op te tekenen
     */
    public void draw(Graphics2D g) {
        if (image != null)
            g.drawImage(image, positie[0], positie[1], width, height, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(positie[0], positie[1], width, height);
        }

        // Mandje boven hoofd
        if (!productsList.isEmpty()) {
            StringBuilder inventoryText = new StringBuilder("Mandje: ");
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
            int textY = positie[1] - 10;

            int padding = 6;
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRoundRect(textX - padding, textY - textHeight + 3,
                    textWidth + padding * 2, textHeight, 10, 10);

            g.setColor(new Color(255, 230, 120));
            g.drawString(inventoryText.toString(), textX, textY);
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

    /**
     * Geeft de lijst van producten die de klant heeft gepakt.
     *
     * @return lijst van Product objecten
     */
    public List<Product> getProductsList() {
        return productsList;
    }

    /**
     * Geeft de index van het huidige segment van de route.
     *
     * @return huidige segmentindex
     */
    public int getCurrentSegmentIndex() {
        return currentSegmentIndex;
    }
}
