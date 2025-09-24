package Model.Logic;

import Model.People.Person;
import Model.Store.Kassa;
import Model.Store.Schap;
import Model.Store.Winkelwagen;

import java.util.ArrayList;
import java.util.List;

public class World
{
    private List<Kassa> kassas = new ArrayList<Kassa>();
    private List<Schap> Schappen =  new ArrayList<Schap>();
    private List<Person> Persons = new ArrayList<Person>();

    public  World()
    {
        kassas.add(new Kassa(null, null));

        Schappen.add(new Schap(0,0));

        Persons.add(new Person(5f, new Integer[]{0,0}, new Integer[]{0,0} , null));
    }
}
