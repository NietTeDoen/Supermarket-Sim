package Model.Logic;

import java.util.*;

/**
 * WorldGraph vertegenwoordigt een graaf van nodes (waypoints) en edges (verbindingen)
 * voor padfinding in de simulatie.
 *
 * Het bevat methodes om nodes toe te voegen, verbindingen te maken en een pad te vinden
 * tussen twee nodes.
 */
public class WorldGraph {

    /** Lijst van alle nodes in de graaf */
    private List<Node> nodes = new ArrayList<>();

    /**
     * Voegt een node toe aan de graaf.
     *
     * @param node de node die toegevoegd wordt
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Verbindt twee nodes met een tweerichtings-edge.
     *
     * @param node1 de eerste node
     * @param node2 de tweede node
     * @param cost de kosten (bijv. afstand) van de verbinding
     */
    public void connect(Node node1, Node node2, float cost) {
        // Voeg edge van node1 naar node2 toe
        node1.edges.add(new Edge(node2, cost));
        // Voeg edge van node2 naar node1 toe (tweerichtingsverkeer)
        node2.edges.add(new Edge(node1, cost));
    }

    /**
     * Vindt een pad tussen start en end node met BFS (Breadth-First Search).
     *
     * @param start de startnode
     * @param end de doelnode
     * @return een lijst van nodes die het pad vormen van start naar end
     */
    public List<Node> findPath(Node start, Node end) {
        // Houdt bij van welke node we op elke node gekomen zijn
        Map<Node, Node> cameFrom = new HashMap<>();
        // Queue voor BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        cameFrom.put(start, null); // startnode heeft geen parent

        // BFS loop
        while (!queue.isEmpty()) {
            Node current = queue.poll(); // haal node uit queue

            // Stop als we het doel bereikt hebben
            if (current.equals(end)) break;

            // Kijk naar alle buur-nodes
            for (Edge edge : current.edges) {
                // Als de buur nog niet bezocht is
                if (!cameFrom.containsKey(edge.target)) {
                    cameFrom.put(edge.target, current); // registreer hoe we er kwamen
                    queue.add(edge.target); // voeg toe aan queue voor verdere verkenning
                }
            }
        }

        // Pad reconstrueren van end naar start
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }

        // Reverse zodat pad loopt van start naar end
        Collections.reverse(path);
        return path;
    }
}
