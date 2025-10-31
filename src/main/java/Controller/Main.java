package Controller;

import Model.Logic.Node;
import Model.Logic.World;
import Model.Logic.WorldGraph;
import Model.People.Klant;
import Model.People.Person;
import Model.Store.Kassa;
import Model.Store.Product;
import Model.Store.Schap;
import Model.Store.SchapType;
import View.SimulationPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * Hoofdklasse van de Supermarket Simulator.
 * <p>
 * Deze klasse initialiseert de wereld, maakt het GUI-frame, laadt nodes, producten en schappen,
 * en start de TickController voor de simulatie.
 * </p>
 */
public class Main {

    /** Het paneel waarop de simulatie wordt getekend */
    public static SimulationPanel panel;

    /** Map van node-namen naar Node-objecten */
    public static Map<String, Node> nodes = new HashMap<>();

    /** Lijst van alle schappen in de winkel */
    public static List<Schap> schappenlijst = new ArrayList<>();

    /** Coördinaten van nodes (relatief) */
    public static HashMap<String, double[]> nodeCordinates = new HashMap<>();

    /** De wereld van de simulatie */
    public static World world = new World();

    /**
     * Standaard constructor voor Main.
     *
     * @throws IOException indien initialisatie faalt
     */
    public Main() throws IOException { }

    /**
     * Startpunt van de applicatie.
     * <p>
     * Initialiseert de GUI, setup van de wereld, nodes, schappen en start de tick-thread.
     * </p>
     *
     * @param args command-line arguments
     * @throws IOException indien initialisatie faalt
     */
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                TickController tickController = new TickController();
                SetupApplication();
                grafen();
                initiateSchappenLijst();

                // Start de tick-thread
                new Thread(() -> {
                    try {
                        tickController.start();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Initialiseert het GUI-frame en de simulatiepanelen.
     *
     * @throws IOException indien een resource (bijv. image) niet geladen kan worden
     */
    private static void SetupApplication() throws IOException {
        JFrame frame = new JFrame("Supermarket Simulator");

        ImageIcon icon = new ImageIcon(Main.class.getResource("/images/Logo.png"));
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel = new SimulationPanel();
        TickController.setPanel(panel);
        Schap.setPanel(panel);
        Kassa.setPanel(panel);

        for (Schap s : schappenlijst) {
            world.addSchap(s);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Initialiseert de schappen en producten in de winkel.
     * <p>
     * Koppelt elk product aan een schap.
     * </p>
     */
    private static void initiateSchappenLijst() {
        schappenlijst.add(new Schap(200, 300, SchapType.Kast1, "Appel"));
        schappenlijst.add(new Schap(400, 300, SchapType.Kast2, "Brood"));
        schappenlijst.add(new Schap(600, 300, SchapType.Kast3, "Melk"));
        schappenlijst.add(new Schap(800, 300, SchapType.Liggend_kast1, "Chips"));
        schappenlijst.add(new Schap(1000, 300, SchapType.Liggend_kast2, "Koekjes"));
    }

    /**
     * Placeholder om klanten te initialiseren.
     * TODO: implementatie toevoegen.
     */
    private void initiatecustomer() {
        // TODO
    }

    /**
     * Initialiseert nodes en grafen voor de winkel.
     * <p>
     * Voegt nodes toe, verbindt ze met edges en test het pad van entrance naar exit.
     * </p>
     */
    private static void grafen() {
        WorldGraph graph = new WorldGraph();

        // Relatieve coördinaten voor nodes
        nodeCordinates.put("entrance", new double[]{0.5234375, 0.8140610545790934});
        nodeCordinates.put("pad1", new double[]{0.59765625, 0.8122109158186864});
        nodeCordinates.put("pad2", new double[]{0.84716796875, 0.81313598519889});
        nodeCordinates.put("liggend-kast-2", new double[]{0.8466796875, 0.5513413506012951});
        nodeCordinates.put("koelkast", new double[]{0.8408203125, 0.35337650323774283});
        nodeCordinates.put("kast1", new double[]{0.64990234375, 0.3089731729879741});
        nodeCordinates.put("liggend-kast-1", new double[]{0.59130859375, 0.5235892691951897});
        nodeCordinates.put("kast2", new double[]{0.4091796875, 0.31267345050878814});
        nodeCordinates.put("kast3", new double[]{0.1787109375, 0.3108233117483811});
        nodeCordinates.put("queue3", new double[]{0.24755859375, 0.4958371877890842});
        nodeCordinates.put("queue2", new double[]{0.25439453125, 0.5772432932469935});
        nodeCordinates.put("queue1", new double[]{0.26123046875, 0.6595744680851063});
        nodeCordinates.put("cashier", new double[]{0.26611328125, 0.788159111933395});
        nodeCordinates.put("exit", new double[]{0.42333984375, 0.8103607770582794});

        // Voeg nodes toe aan graf
        for (Map.Entry<String, double[]> entry : nodeCordinates.entrySet()) {
            String name = entry.getKey();
            double[] coords = entry.getValue();
            Node node = new Node(name, (float) coords[0], (float) coords[1]);
            graph.addNode(node);
            nodes.put(name, node);
        }

        // Verbindingen (edges)
        graph.connect(nodes.get("entrance"), nodes.get("pad1"), 1);
        graph.connect(nodes.get("pad1"), nodes.get("pad2"), 2);
        graph.connect(nodes.get("pad1"), nodes.get("liggend-kast-1"), 2);
        graph.connect(nodes.get("pad2"), nodes.get("liggend-kast-2"), 2);
        graph.connect(nodes.get("liggend-kast-1"), nodes.get("kast1"), 2);
        graph.connect(nodes.get("liggend-kast-2"), nodes.get("koelkast"), 2);
        graph.connect(nodes.get("koelkast"), nodes.get("kast1"), 2);
        graph.connect(nodes.get("kast1"), nodes.get("kast2"), 3);
        graph.connect(nodes.get("kast2"), nodes.get("kast3"), 2);
        graph.connect(nodes.get("kast2"), nodes.get("queue2"), 2);
        graph.connect(nodes.get("kast3"), nodes.get("queue2"), 2);
        graph.connect(nodes.get("queue2"), nodes.get("queue1"), 1);
        graph.connect(nodes.get("queue1"), nodes.get("cashier"), 1);
        graph.connect(nodes.get("cashier"), nodes.get("exit"), 2);

        // Test pad van entrance naar exit
        List<Node> path = graph.findPath(nodes.get("entrance"), nodes.get("exit"));

        System.out.println("Path from Entrance to ShelfB:");
        for (Node n : path) {
            System.out.print(n.name + " -> ");
        }
        System.out.println("END");
    }
}
