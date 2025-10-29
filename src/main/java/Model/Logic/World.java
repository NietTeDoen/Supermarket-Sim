package Model.Logic;

import Controller.Main;
import Controller.TickController;
import Model.People.Klant;
import Model.People.Person;
import Model.Store.Kassa;
import Model.Store.Schap;
import Model.Store.SchapType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static Controller.Main.nodeCordinates;

/**
 * De wereld bevat alle objecten die getekend worden.
 * Hier voeg je schappen, kassa’s, en personen toe.
 */
public class World {
    public static List<Kassa> kassas = new ArrayList<>();
    public static List<Schap> schappen = new ArrayList<>();
    public static List<Klant> persons = new ArrayList<>();

    private static JPanel panel;

    public World() {

        // Kassas (relatieve coördinaten)
        kassas.add(new Kassa(0.0977, 0.3819));

// Schappen (relatieve coördinaten)
        schappen.add(new Schap(0.5664, 0.1042, SchapType.Kast1));
        schappen.add(new Schap(0.3125, 0.1042, SchapType.Kast2));
        schappen.add(new Schap(0.0977, 0.1042, SchapType.Kast3));
        schappen.add(new Schap(0.4102, 0.3125, SchapType.Liggend_kast1));
        schappen.add(new Schap(0.6973, 0.3750, SchapType.Liggend_kast2));
        schappen.add(new Schap(0.7813, 0.0903, SchapType.Koelkast));
    }

    public void draw(Graphics g) {
        for (Kassa k : kassas) k.draw(g);
        for (Schap s : schappen) s.draw(g);
        for (Klant p : persons) p.draw(g);
    }

    // Add-methodes
    public void addKassa(Kassa k) { kassas.add(k); }
    public void addSchap(Schap s) { schappen.add(s); }
    public void addPerson(Klant p) { persons.add(p); }

    // Getters
    public List<Kassa> getKassas() { return kassas; }
    public List<Schap> getSchappen() { return schappen; }
    public static List<Klant> getPersons() { return persons; }

    public static void RemovePerson(Klant p) { persons.remove(p);}
    public static Klant CreatePerson() {
        Node entrance = Main.nodes.get("entrance");
        if (entrance == null) return null;

        JPanel panel = TickController.panel;
        if (panel == null || panel.getWidth() == 0 || panel.getHeight() == 0) return null;

        // Zet relatieve coördinaten om naar pixelpositie
        int x = (int) (entrance.x * panel.getWidth());
        int y = (int) (entrance.y * panel.getHeight());

        // Genereer padsegmenten (meerdere trajecten)
        List<List<Node>> pathSegments = NodelistGenerator(entrance);

        // Maak de klant met deze segmenten
        Klant person = new Klant(new int[]{x, y}, pathSegments, "dummy");

        persons.add(person);
        return person;
    }

    /**
     * Genereert een lijst van padsegmenten. Elk segment loopt van een startnode
     * naar een doel (bijv. schap), en de laatste naar de uitgang.
     */
    public static List<List<Node>> NodelistGenerator(Node startNode) {
        Set<String> exclude = Set.of(
                "queue", "queue1", "queue2", "queue3",
                "kassa", "entrance", "cashier", "exit", "pad1", "pad2"
        );

        List<Node> possibleTargets = new ArrayList<>();
        for (String key : Main.nodeCordinates.keySet()) {
            if (!exclude.contains(key.toLowerCase())) {
                possibleTargets.add(Main.nodes.get(key));
            }
        }

        if (possibleTargets.isEmpty()) return List.of();

        // Kies een paar willekeurige schappen
        Collections.shuffle(possibleTargets);
        int count = Math.min(3, possibleTargets.size());
        List<Node> chosenTargets = possibleTargets.subList(0, count);

        List<List<Node>> allSegments = new ArrayList<>();
        Node current = startNode;

        // Maak per target een apart padsegment
        for (Node target : chosenTargets) {
            List<Node> pathSegment = WorldGraph.findPath(current, target);
            if (pathSegment != null && !pathSegment.isEmpty()) {
                allSegments.add(new ArrayList<>(pathSegment));
                current = target;
            }
        }

        Node Queue1 = Main.nodes.get("queue2");
        if (Queue1 != null) {
            List<Node> Queue1path = WorldGraph.findPath(current, Queue1);
            if (Queue1path != null && !Queue1path.isEmpty()) {
                allSegments.add(new ArrayList<>(Queue1path));
                current = Queue1;
            }
        }

        // Voeg het pad naar de uitgang toe als laatste segment
        Node exit = Main.nodes.get("exit");
        if (exit != null) {
            List<Node> exitPath = WorldGraph.findPath(current, exit);
            if (exitPath != null && !exitPath.isEmpty()) {
                allSegments.add(new ArrayList<>(exitPath));
            }
        }

        // Debug output
        System.out.println("Generated " + allSegments.size() + " path segments:");
        for (int i = 0; i < allSegments.size(); i++) {
            System.out.print(" Segment " + (i + 1) + ": ");
            for (Node n : allSegments.get(i)) System.out.print(" -> " + n.name);
            System.out.println();
        }

        return allSegments;
    }



}
