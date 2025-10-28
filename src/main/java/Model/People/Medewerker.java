package Model.People;

import Model.Logic.Node;

import java.util.List;

public class Medewerker extends Person {
    private String Taak;

    public Medewerker(int[] positie, List<Node> target, String sprite, String Taak) {
        super(positie, target, sprite);
        this.Taak = Taak;
    }
}
