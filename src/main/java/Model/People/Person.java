package Model.People;

import Model.Logic.Node;
import Controller.TickController;

import java.awt.*;
import java.util.List;

public class Person {
    private int[] positie;
    private String sprite;

    private List<Node> path;  // het pad dat deze persoon volgt
    private int pathIndex = 0; // node in pad waar we naartoe bewegen
    private float speed = 2f;

    public Person(int[] positie, List<Node> path, String sprite) {
        this.positie = positie;
        this.path = path;
        this.sprite = sprite;
    }

    public void update() {
        moveAlongPath();
    }

    private void moveAlongPath() {
        if (path == null || pathIndex >= path.size()) return;

        Node target = path.get(pathIndex);

        int targetX = (int) (target.x * TickController.getPanelWidth());
        int targetY = (int) (target.y * TickController.getPanelHeight());

        float dx = targetX - positie[0];
        float dy = targetY - positie[1];
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            // target bereikt
            positie[0] = targetX;
            positie[1] = targetY;
            pathIndex++;
        } else {
            positie[0] += (dx / distance) * speed;
            positie[1] += (dy / distance) * speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(positie[0], positie[1], 20, 20);
    }
}
