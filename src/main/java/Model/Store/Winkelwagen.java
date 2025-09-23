package Model.Store;

import java.util.List;
import java.util.UUID;

public class Winkelwagen {
    private UUID PersonID;
    private List<Product> ProductList;

    public Winkelwagen(UUID PersonID, List<Product> ProductList) {
        this.PersonID = PersonID;
        this.ProductList = ProductList;
    }

    public List<Product> AddProduct(Product Product) {
        ProductList.add(Product);
        return ProductList;
    }
}
