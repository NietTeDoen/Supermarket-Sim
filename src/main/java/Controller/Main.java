package Controller;

import Model.Logic.Node;
import Model.Logic.WorldGraph;
import Model.People.Klant;
import Model.People.Person;
import Model.Store.Product;
import Model.Store.Schap;
import View.SimulationPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static SimulationPanel panel;

    static {
        try {
            panel = new SimulationPanel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Node> nodes = new HashMap<>();
    public static Schap[] schappenlijst = new Schap[5];

    public static Product[] productlijst =  new Product[5];

    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                TickController tickController = new TickController();
                SetupApplication();
                grafen();
                initiateSchappenLijst();
                testperson();
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

    private static void SetupApplication() throws IOException {
        JFrame frame = new JFrame("Supermarket Simulator");

        ImageIcon icon = new ImageIcon(Main.class.getResource("/images/Logo.png"));
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        TickController.setPanel(panel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void testperson(){
        TickController tickController = new TickController();

        float tempx = (float) 0.5234375 * panel.width;
        float tempy = (float) 0.8140610545790934 * panel.height;
        Node[] tempnode = {nodes.get("exit")};

        Integer[] startposition = {(int) tempx, (int) tempy};
        Person person = new Klant(5f, startposition, tempnode, "", null);
        tickController.addCharacter(person);
    }

    private static void initiateSchappenLijst(){
        schappenlijst[0] = new Schap(0,0);
        schappenlijst[1] = new Schap(0,0);
        schappenlijst[2] = new Schap(0,0);
        schappenlijst[3] = new Schap(0,0);
        schappenlijst[4] = new Schap(0,0);
        schappenlijst[5] = new Schap(0,0);

        productlijst[0] = new Product("Cola", 1.99);
        productlijst[1] = new Product("Milk", 1.99);
        productlijst[2] = new Product("Potatoes", 1.99);
        productlijst[3] = new Product("Pork", 1.99);
        productlijst[4] = new Product("Carrot", 1.99);
        productlijst[5] = new Product("Cookie", 1.99);

        for (int i = 0; i < 5; i++) {
            schappenlijst[i].AddProduct(productlijst[i], 24);
        }
    }

    private void initiatecustomer(){
        //TODO
    }


    private static void grafen(){
        // 1. Maak een WorldGraph
        WorldGraph graph = new WorldGraph();

        HashMap<String, double[]> nodeCordinates = new HashMap<>();

        //Dit zijn de relatieve cordinaten. die doen we x de screensize om de goede punten te plaatsen.
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

        // 2. Voeg een paar dummy nodes toe
        for (Map.Entry<String, double[]> entry : nodeCordinates.entrySet()) {
            String name = entry.getKey();
            double[] coords = entry.getValue();
            float x = (float) coords[0];
            float y = (float) coords[1];
            Node node = new Node(name, x, y);
            graph.addNode(node);
            nodes.put(name, node);
        }

        // 3. Maak verbindingen (edges)
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


        // 4. Test de findPath methode
        List<Node> path = graph.findPath(nodes.get("entrance"), nodes.get("exit"));

        // 5. Print het pad
        System.out.println("Path from Entrance to ShelfB:");
        for (Node n : path) {
            System.out.print(n.name + " -> ");
        }
        System.out.println("END");
    }

}
