package Model.People;

public class Medewerker extends Person {
    private String Taak;

    public Medewerker(Float speed, Integer[] positie, Integer[] target, String sprite, String Taak) {
        super(speed, positie, target, sprite);
        this.Taak = Taak;
    }
}
