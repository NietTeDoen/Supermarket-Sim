package Model.Logic;

import java.util.*;

public class WorldGraph {
    private List<Node> nodes = new ArrayList<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void connect (Node node1, Node node2, float cost) {
        node1.edges.add(new Edge(node2, cost));
        node2.edges.add(new Edge(node1, cost));
    }

    public List<Node> findPath(Node start, Node end) {
        Map<Node, Node> cameFrom = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(end)) break;
            for  (Edge edge : current.edges) {
                if (!cameFrom.containsKey(edge.target)){
                    cameFrom.put(edge.target, current);
                    queue.add(edge.target);
                }
            }
        }
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null){
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
