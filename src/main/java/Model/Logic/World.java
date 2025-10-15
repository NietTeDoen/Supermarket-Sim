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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * De wereld bevat alle objecten die getekend worden.
 * Hier voeg je schappen, kassa’s, en personen toe.
 */
public class World {
    private List<Kassa> kassas = new ArrayList<>();
    private List<Schap> schappen = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();

    public World() {

        kassas.add(new Kassa(250, 550));

        schappen.add(new Schap(1450, 150, SchapType.Kast1));
        schappen.add(new Schap(800, 150, SchapType.Kast2));
        schappen.add(new Schap(250, 150, SchapType.Kast3));
        schappen.add(new Schap(1050, 450, SchapType.Liggend_kast1));
        schappen.add(new Schap(1785, 540, SchapType.Liggend_kast2));
        schappen.add(new Schap(2000, 130, SchapType.Koelkast));

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
