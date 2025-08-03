package thinking.genericity;

import cn.qingweico.supplier.ObjectFactory;

/**
 * @author zqw
 * @date 2021/4/11
 */
class Store {
    public static void main(String[] args) {
        Product product = ObjectFactory.create(Product.class, true);
        product.priceChange(20);
        System.out.println(product);
    }
}

class Product {
    private final int id;
    private final String description;
    private double price;

    public Product(int idNumber, String desc, double price) {
        id = idNumber;
        description = desc;
        this.price = price;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return id + ": " + description + ", price: $" + price;
    }

    public void priceChange(double change) {
        price += change;
    }
}
