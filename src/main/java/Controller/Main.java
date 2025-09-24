package Controller;

import Model.Logic.Node;
import Model.Logic.WorldGraph;
import View.SimulationPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Starting Tick Controller...");
                TickController tickController = new TickController();
                SetupApplication();
                testgrafen();
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
            frame.setVisible(true);

            try {
                frame.add(new SimulationPanel());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    private static void testgrafen(){
        // 1. Maak een WorldGraph
        WorldGraph graph = new WorldGraph();

        // 2. Voeg een paar dummy nodes toe
        Node entrance = new Node("Entrance", 0, 0);
        Node shelfA = new Node("ShelfA", 5, 0);
        Node shelfB = new Node("ShelfB", 5, 5);
        Node cashier = new Node("Cashier", 0, 5);

        graph.addNode(entrance);
        graph.addNode(shelfA);
        graph.addNode(shelfB);
        graph.addNode(cashier);

        // 3. Maak verbindingen (edges)
        graph.connect(entrance, shelfA, 5);
        graph.connect(entrance, cashier, 5);
        graph.connect(shelfA, shelfB, 5);
        graph.connect(shelfB, cashier, 5);

        // 4. Test de findPath methode
        List<Node> path = graph.findPath(entrance, shelfB);

        // 5. Print het pad
        System.out.println("Path from Entrance to ShelfB:");
        for (Node n : path) {
            System.out.print(n.name + " -> ");
        }
        System.out.println("END");
    }

}
