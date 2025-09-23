package Model.Store;

import java.util.Map;

public class Schap {
    private Map<Product, Integer> Voorraad;
    private int[] positie;

    public Schap(int x, int y) {
        this.positie = new int[x * y];
    }

    public void AddProduct(Product product, int quantity) {
        Voorraad.put(product, Voorraad.get(product) + quantity);
    }

    public boolean RemoveProduct(Product product) {
        if (Voorraad.containsKey(product) &&  Voorraad.get(product) > 0) {
            Voorraad.put(product, Voorraad.get(product) - 1);
            return true;
        }
        return false;
    }
}
