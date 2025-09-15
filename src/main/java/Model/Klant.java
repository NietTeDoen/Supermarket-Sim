package Model;

import java.util.List;
import java.util.UUID;

public class Klant extends  Person {
    private List<Product> ProductsList;

    public Klant(Float speed, Integer[] positie, Integer[] target, String sprite, List<Product> ProductsList) {
        super(speed, positie, target, sprite);
        this.ProductsList = ProductsList;
    }
}
