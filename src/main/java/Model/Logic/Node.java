package Model.Logic;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String name;
    public float x,y;
    public List<Edge> edges = new ArrayList<>();

    public Node(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }


}
