package Model.People;

import Model.Logic.Node;

public class Medewerker extends Person {
    private String Taak;

    public Medewerker(Float speed, Integer[] positie, Node[] target, String sprite, String Taak) {
        super(speed, positie, target, sprite);
        this.Taak = Taak;
    }
}
