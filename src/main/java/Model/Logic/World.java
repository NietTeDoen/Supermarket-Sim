/*
package Model.Logic;

import Model.People.Person;
import Model.Store.Kassa;
import Model.Store.Schap;
import Model.Store.Winkelwagen;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World
{
    public List<Kassa> kassas = new ArrayList<Kassa>();
    public List<Schap> Schappen =  new ArrayList<Schap>();
    public List<Person> Persons = new ArrayList<Person>();

    public  World()
    {
        // 👇 Spawn een testkassa
        kassas.add(new Kassa(200, 700));

        // 👇 Spawn een testschap
        Schappen.add(new Schap(400, 300));

    }

    public void draw(Graphics g) {
        for (Kassa k : kassas) {
            k.draw(g);
        }

        for (Schap s : Schappen) {
            s.draw(g);
        }

        for (Person p : Persons) {
            p.draw(g);
        }
    }
    public void addKassa(Kassa k) {
        kassas.add(k);
    }

    public void addSchap(Schap s) {
        Schappen.add(s);
    }

    public void addPerson(Person p) {
        Persons.add(p);
    }

    // 👇 Getters (indien andere klassen de lijsten moeten zien)
    public List<Kassa> getKassas() {
        return kassas;
    }

    public List<Schap> getSchappen() {
        return Schappen;
    }

    public List<Person> getPersons() {
        return Persons;
    }
}
*/


package Model.Logic;

import Model.People.Person;
import Model.Store.Kassa;
import Model.Store.Schap;
import Model.Store.SchapType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Person> getPersons() { return persons; }
}
