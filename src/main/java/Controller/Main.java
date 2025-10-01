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
                grafen();
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

    private static void grafen(){
        // 1. Maak een WorldGraph
        WorldGraph graph = new WorldGraph();

        // 2. Voeg een paar dummy nodes toe
        Node entrance = new Node("Entrance", 0, 0);
        Node pad1 = new Node("Pad1", 0, 0);
        Node pad2 = new Node("Pad2", 0, 0);
        Node liggend_kast_1 = new Node("Liggend-kast-1", 5, 5);
        Node kast1 = new Node("kast1", 5, 0);
        Node kast2 = new Node("kast2", 5, 5);
        Node koelkast = new Node("koelkast", 5, 5);
        Node liggend_kast_2 = new Node("liggend-kast-2", 5, 5);
        Node kast3 = new Node("kast3", 5, 5);
        Node queue2 = new Node("queue2", 5, 5);
        Node queue1 = new Node("queue1", 5, 5);
        Node cashier = new Node("Cashier", 0, 5);
        Node exit =  new Node("Exit", 0, 5);

        graph.addNode(entrance);
        graph.addNode(pad1);
        graph.addNode(pad2);
        graph.addNode(liggend_kast_1);
        graph.addNode(kast1);
        graph.addNode(kast2);
        graph.addNode(koelkast);
        graph.addNode(liggend_kast_2);
        graph.addNode(kast3);
        graph.addNode(queue2);
        graph.addNode(queue1);
        graph.addNode(cashier);
        graph.addNode(exit);

        // 3. Maak verbindingen (edges)
        graph.connect(entrance, pad1, 1);
        graph.connect(pad1, pad2, 2);
        graph.connect(pad1, liggend_kast_1, 2);
        graph.connect(pad2, liggend_kast_2, 2);
        graph.connect(liggend_kast_1, kast1, 2);
        graph.connect(liggend_kast_2, koelkast, 2);
        graph.connect(koelkast, kast1, 2);
        graph.connect(kast1, kast2, 3);
        graph.connect(kast2, kast3, 2);
        graph.connect(kast2, queue2, 2);
        graph.connect(kast3, queue2, 2);
        graph.connect(queue2, queue1, 1);
        graph.connect(queue1, cashier, 1);
        graph.connect(cashier, exit, 2);


        // 4. Test de findPath methode
        List<Node> path = graph.findPath(entrance, exit);

        // 5. Print het pad
        System.out.println("Path from Entrance to ShelfB:");
        for (Node n : path) {
            System.out.print(n.name + " -> ");
        }
        System.out.println("END");
    }

}
