package Model.People;

import Controller.TickController;
import Model.Logic.Node;

import java.util.UUID;

public class Person {
    private UUID PersonId;
    private Float Speed;
    private Integer[] Positie;
    private Node[] Target;
    private String sprite;

    private Node startNode;
    private Node endNode;

    public Person(Float Speed, Integer[] Positie, Node[] Target, String sprite) {
        PersonId = UUID.randomUUID();
        this.Speed = Speed;
        this.Positie = Positie;
        this.Target = Target;
        this.sprite = sprite;
    }

    public UUID getPersonId() {
        return PersonId;
    }

    public Boolean setSprite(String sprite) {
        this.sprite = sprite;
        return true;
    }

    public void update(){
        if(checkIfReached()){
            moveTo();
        }
    }

    private void moveTo(){

    }

    private boolean checkIfReached(){
        if(endNode.x == (float) Positie[0] && endNode.y == (float) Positie[1]){
            return true;
        }
        return false;
    }

    public void setTarget(Node[] target){
        this.Target = target;
    }

    public void despawn(){
        TickController.removeCharacter(this);
    }

    public void spawn(){
        TickController.addCharacter(this);
    }

}
