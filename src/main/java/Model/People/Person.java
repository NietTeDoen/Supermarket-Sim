package Model.People;

import java.util.UUID;

public class Person {
    private UUID PersonId;
    private Float Speed;
    private Integer[] Positie;
    private Integer[] Target;
    private String sprite;

    public Person(Float Speed, Integer[] Positie, Integer[] Target, String sprite) {
        PersonId = UUID.randomUUID();
        this.Speed = Speed;
        this.Positie = Positie;
        this.Target = Target;
        this.sprite = sprite;
    }

    public UUID getPersonId() {
        return PersonId;
    }

    public Integer[] setTarget(Integer[] Target) {
        this.Target = Target;
        return this.Target;
    }

    public Boolean setSprite(String sprite) {
        this.sprite = sprite;
        return true;
    }
}
