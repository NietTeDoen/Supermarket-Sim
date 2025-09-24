package Model.Logic;

public class Edge {
    public Node target;
    public float cost;

    public Edge(Node target, float cost) {
        this.target = target;
        this.cost = cost;
    }

}
