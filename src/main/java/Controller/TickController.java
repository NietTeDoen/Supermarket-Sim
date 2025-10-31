package Controller;

import Model.Logic.World;
import Model.People.Klant;
import Model.People.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TickController beheert de "ticks" van de simulatie.
 * Elke tick update alle personen in de wereld, verwerkt spawning van nieuwe klanten
 * en hertekent het paneel.
 *
 * <p>De simulatie draait op een vast aantal ticks per seconde (TICKS_PER_SECOND).</p>
 */
public class TickController {
    /** Aantal updates per seconde (30 = 30fps) */
    final double TICKS_PER_SECOND = 30.0;

    /** Hoeveel milliseconden tussen ticks */
    final double SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    /** Tijdstip voor de volgende tick */
    double nextTick = System.currentTimeMillis();

    /** Geeft aan of de tick loop actief is */
    boolean running = true;

    /** Lijst van alle klanten in de simulatie */
    static List<Klant> personList = new ArrayList<>();

    /** Teller voor debug/interval logging */
    int count = 0;

    /** Paneel waarop de simulatie wordt getekend */
    public static JPanel panel;

    /**
     * Start de tick-loop van de simulatie.
     * <p>Update alle personen in de wereld en repaints het panel.</p>
     *
     * @throws InterruptedException als de thread onderbroken wordt tijdens sleep
     */
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

    /**
     * Stelt het panel in waarop de simulatie getekend wordt.
     *
     * @param p het JPanel om te gebruiken
     */
    public static void setPanel(JPanel p) {
        panel = p;
    }

    /**
     * Voegt een klant toe aan de simulatie.
     *
     * @param person de klant die toegevoegd wordt
     */
    public static void addCharacter(Klant person){
        personList.add(person);
    }


    /**
     * Verwijdert een klant uit de simulatie en uit de wereld.
     *
     * @param person de klant die verwijderd wordt
     */
    public static void removeCharacter(Klant person){
        personList.remove(person);
        World.RemovePerson(person);
    }

    /**
     * Voert één tick uit: update alle personen en verwijdert despawned personen.
     *
     * @throws InterruptedException als de thread onderbroken wordt tijdens sleep
     */
    private void tick() throws InterruptedException {
        checkCustomerAmount();
        World.checkSchap();

        for (Person person : World.getPersons()) {
            person.update(); // update markeert eventueel despawned
        }

        // Verwijder alle gedespawnte personen na de updates
        World.getPersons().removeIf(Person::isDespawned);
        personList.removeIf(Person::isDespawned);

        panel.repaint();

        if(count < 30)
            count++;
        else {
            // Optioneel debug: System.out.println("Tick " + (System.currentTimeMillis() - nextTick));
            count = 0;
        }
    }

    /**
     * Converteert relatieve coördinaten (0.0 - 1.0) naar pixels op het panel.
     *
     * @param relX relatieve X-coördinaat
     * @param relY relatieve Y-coördinaat
     * @return Point met pixel-coördinaten
     */
    public static Point convertRelative(double relX, double relY) {
        int x = (int)(relX * panel.getWidth());
        int y = (int)(relY * panel.getHeight());
        return new Point(x, y);
    }

    /**
     * Controleert hoeveel klanten er zijn en spawnt nieuwe klanten indien nodig.
     *
     * @return het actuele aantal klanten in de simulatie
     */
    public static int checkCustomerAmount() {
        List<Klant> persons = World.getPersons();

        // Alleen spawn als panel zichtbaar is
        if(panel.getWidth() == 0 || panel.getHeight() == 0) return persons.size();

        // Spawn maximaal tot 10 personen
        while (persons.size() < 10) {
            Klant p = World.CreatePerson();
            if (p != null) TickController.addCharacter(p);
        }

        return persons.size();
    }

    /**
     * Geeft de breedte van het panel in pixels.
     *
     * @return breedte in pixels, of 1920 als panel null is
     */
    public static int getPanelWidth() {
        return panel != null ? panel.getWidth() : 1920;
    }

    /**
     * Geeft de hoogte van het panel in pixels.
     *
     * @return hoogte in pixels, of 1080 als panel null is
     */
    public static int getPanelHeight() {
        return panel != null ? panel.getHeight() : 1080;
    }

}
