package Model.Store;

public class Product {
    private String Name;
    private Double Price;

    public Product(String name, Double price) {
        this.Name = name;
        this.Price = price;
    }

    // ✅ Voeg deze twee getters toe
    public String getNaam() {
        return Name;
    }

    public Double getPrijs() {
        return Price;
    }

    // Optioneel: setters (handig als je prijs later wil aanpassen)
    public void setNaam(String name) {
        this.Name = name;
    }

    public void setPrijs(Double price) {
        this.Price = price;
    }

    @Override
    public String toString() {
        return Name + " (€" + Price + ")";
    }
}
