package Controller;

import Model.People.Person;
import View.SimulationPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TickController {
    final double TICKS_PER_SECOND = 30.0; //The amount it updates every second, so 30 means 30fps
    final double SKIP_TICKS = 1000 /  TICKS_PER_SECOND;
    double nextTick = System.currentTimeMillis();
    boolean running = true;

    static List<Person> personList = new ArrayList<>();

    int count = 0;

    private static JPanel panel;

    public void start() throws InterruptedException {
        System.out.println("Starting Tick Controller");
        while (running) {
            while (System.currentTimeMillis() > nextTick) {
                tick();
                nextTick += SKIP_TICKS;
            }
            Thread.sleep(1);
        }

    }

    public static void setPanel(JPanel p) {
        panel = p;
    }

    public static void addCharacter (Person person){
        personList.add(person);
    }

    public static void removeCharacter (Person person){
        personList.remove(person);
    }

    private void tick(){
        //update person
        for  (Person person : personList) {

        }

        panel.repaint();
        if(count < 30)
            count++;
        else {
//            System.out.println("Tick " + (System.currentTimeMillis() - nextTick));
            count = 0;
        }
    }

    public static Point convertRelative(double relX, double relY) {
        int x = (int)(relX * panel.getWidth());
        int y = (int)(relY * panel.getHeight());
        return new Point(x, y);
    }

}
