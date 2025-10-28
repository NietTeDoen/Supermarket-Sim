package Model.People;

import Model.Logic.Node;
import Model.Store.Product;

import java.util.List;

public class Klant extends  Person {
    private List<Product> ProductsList;

    public Klant(int[] positie, List<Node> target, String sprite, List<Product> ProductsList) {
        super( positie, target, sprite);
        this.ProductsList = ProductsList;
    }
}
