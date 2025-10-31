package Model.People;

import Model.Logic.Node;
import Controller.TickController;
import Model.Logic.World;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Person {
    protected int[] positie;         // protected ipv private
    protected String sprite;         // protected ipv private

    protected List<Node> path;       // protected ipv private
    protected int pathIndex = 0;     // node in pad waar we naartoe bewegen
    protected float speed = 5f;      // protected ipv private
    protected Image image;            // protected ipv private

    private int width = 100;         // of je sprite breedte
    private int height = 150;        // of je sprite hoogte

    private boolean despawned = false;

    public Person(int[] positie, List<Node> path, String sprite) {
        this.positie = positie;
        this.path = path;
        this.sprite = sprite;
        loadImage();
    }

    private void loadImage() {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/Klant.png"));
            image = icon.getImage();
        } catch (NullPointerException e) {
            System.out.println("Image not found! Zorg dat het pad klopt.");
            image = null;
        }
    }

    public void update() {
        if (path == null || pathIndex >= path.size()){
            Despawncharacter();
            return;
        }

        Node target = path.get(pathIndex);
        int targetX = (int) (target.x * TickController.getPanelWidth()) - width / 2;
        int targetY = (int) (target.y * TickController.getPanelHeight()) - height;

        float dx = targetX - positie[0];
        float dy = targetY - positie[1];
        float distance = (float) Math.sqrt(dx*dx + dy*dy);

        if (distance < speed) {
            positie[0] = targetX;
            positie[1] = targetY;
            pathIndex++;
        } else {
            positie[0] += (dx / distance) * speed;
            positie[1] += (dy / distance) * speed;
        }
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, positie[0], positie[1], width , height, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(positie[0], positie[1], 20, 20);
        }
    }

    public void Despawncharacter() {
        despawned = true;
    }

    public boolean isDespawned() {
        return despawned;
    }
}
