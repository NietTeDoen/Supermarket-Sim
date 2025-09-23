package Model.Store;
import java.util.List;


public class Kassa {
    private List<Product> ScannedItem;
    private Double Prijs;

    public Kassa(List<Product> ScannedItem,  Double Prijs) {
        this.ScannedItem = ScannedItem;
        this.Prijs = Prijs;
    }

    public Double getPrijs() {
        return Prijs;
    }

    public List<Product> getScannedItems() {
        return ScannedItem;
    }

    public Boolean AddProduct(Product product) {
        return ScannedItem.add(product);
    }

    public Boolean RemoveProduct(Product product) {
        return ScannedItem.remove(product);
    }

}
