package Model.Logic;

import Controller.Main;
import Controller.TickController;
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
    public static List<Person> persons = new ArrayList<>();

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
        for (Person p : persons) p.draw(g);
    }

    // Add-methodes
    public void addKassa(Kassa k) { kassas.add(k); }
    public void addSchap(Schap s) { schappen.add(s); }
    public void addPerson(Person p) { persons.add(p); }

    // Getters
    public List<Kassa> getKassas() { return kassas; }
    public List<Schap> getSchappen() { return schappen; }
    public static List<Person> getPersons() { return persons; }

    public static void RemovePerson(Person p) { persons.remove(p);}
    public static Person CreatePerson() {
        Node entrance = Main.nodes.get("entrance");
        if (entrance == null) return null;
        if (Main.panel.getWidth() == 0 || Main.panel.getHeight() == 0) return null;

        // Haal panel op
        JPanel panel = TickController.panel;
        if (panel == null || panel.getWidth() == 0 || panel.getHeight() == 0) return null;

        // Zet relatieve coördinaten om naar pixelpositie
        int x = (int) (entrance.x * panel.getWidth());
        int y = (int) (entrance.y * panel.getHeight());

        // Genereer een willekeurig pad van nodes inclusief schappen en exit
        Node[] pathNodes = NodelistGenerator(entrance);

        // Maak de persoon met het pad
        Person person = new Person(new int[]{x, y}, Arrays.asList(pathNodes), "dummy");

        // Voeg toe aan de wereld
        persons.add(person);

        return person;
    }



    public static Node[] NodelistGenerator(Node startNode) {
        // nodes die geen targets mogen zijn
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

        if (possibleTargets.isEmpty()) return new Node[0];

        // Kies een paar random targets
        Collections.shuffle(possibleTargets);
        int count = Math.min(3, possibleTargets.size());
        List<Node> chosenTargets = possibleTargets.subList(0, count);

        // Bouw volledig pad via WorldGraph.findPath
        List<Node> fullPath = new ArrayList<>();
        Node current = startNode;

        for (Node target : chosenTargets) {
            List<Node> pathSegment = WorldGraph.findPath(current, target);
            if (pathSegment != null && !pathSegment.isEmpty()) {
                if (!fullPath.isEmpty()) pathSegment.remove(0); // voorkom duplicatie
                fullPath.addAll(pathSegment);
                current = target;
            }
        }

        // Voeg exit toe
        Node exit = Main.nodes.get("exit");
        if (exit != null) {
            List<Node> exitPath = WorldGraph.findPath(current, exit);
            if (exitPath != null && !exitPath.isEmpty()) {
                if (!fullPath.isEmpty()) exitPath.remove(0);
                fullPath.addAll(exitPath);
            }
        }

        // Debug output
        String debug = "";
        for (Node n : fullPath) debug += " | " + n.name;
        System.out.println(debug);

        return fullPath.toArray(new Node[0]);
    }


}
