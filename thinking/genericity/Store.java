package thinking.genericity;

/**
 * @author:qiming
 * @date: 2021/4/11
 */
public class Store {
}
class Product {
    private final int id;
    private final String description;
    private double price;
    public Product(int IDNumber, String desc, double price) {
        id = IDNumber;
        description = desc;
        this.price = price;
        System.out.println(toString());
    }
    public String toString() {
        return id + ": " + description + ", price: $" + price;
    }
    public void priceChange(double change) {
        price += change;
    }
}
