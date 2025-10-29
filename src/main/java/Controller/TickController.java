package Controller;

import Model.Logic.World;
import Model.People.Klant;
import Model.People.Person;
import View.SimulationPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class TickController {
    final double TICKS_PER_SECOND = 30.0; //The amount it updates every second, so 30 means 30fps
    final double SKIP_TICKS = 1000 /  TICKS_PER_SECOND;
    double nextTick = System.currentTimeMillis();
    boolean running = true;

    static List<Klant> personList = new ArrayList<>();

    int count = 0;

    public static JPanel panel;

    public void start() throws InterruptedException {
        Thread.sleep(50); // 50 ms wachten zodat panel volledig zichtbaar is
        System.out.println("Starting Tick Controller");
        personList = World.getPersons();
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

    public static void addCharacter (Klant person){
        personList.add(person);
    }

    public static void removeCharacter (Klant person){
        personList.remove(person);
        World.RemovePerson(person);
    }

    private void tick() throws InterruptedException{
        checkCustomerAmount();

        for (Person person : World.getPersons()) {
            person.update(); // update markeert eventueel despawned
        }

        // Na alle updates: verwijder alle gedespawnte personen
        World.getPersons().removeIf(Person::isDespawned);
        personList.removeIf(Person::isDespawned);

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

    public static int checkCustomerAmount() {
        List<Klant> persons = World.getPersons();

        // Alleen spawn als panel al zichtbaar is
        if(panel.getWidth() == 0 || panel.getHeight() == 0) return persons.size();

        // Spawn maximaal tot 5 personen
        while (persons.size() < 1) {
            Klant p = World.CreatePerson();
            if (p != null) TickController.addCharacter(p);
        }

        return persons.size();
    }



    public static int getPanelWidth() {
        return panel != null ? panel.getWidth() : 1920;
    }

    public static int getPanelHeight() {
        return panel != null ? panel.getHeight() : 1080;
    }


}

