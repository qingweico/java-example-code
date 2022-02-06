package design.factory;

/**
 * 抽象工厂方法
 * @author zqw
 * @date 2021/12/22
 */
public abstract class AbstractFactory {
    /**
     * The factory of Electronic Product
     * @return ElectronicProduct
     */
    abstract ElectronicProduct createElectronicProduct();

    /**
     *  The factory of Food Product
     * @return FoodProduct
     */
    abstract FoodProduct createFoodProduct();
}


class ConcreteFactory extends AbstractFactory {

    @Override
    ElectronicProduct createElectronicProduct() {
        return new Computer();
    }

    @Override
    FoodProduct createFoodProduct() {
        return new Rice();
    }
}

class OtherFactory extends AbstractFactory {

    @Override
    ElectronicProduct createElectronicProduct() {
        return new Television();
    }

    @Override
    FoodProduct createFoodProduct() {
        return new Vegetable();
    }
}

class AbstractFactoryClient {
    public static void main(String[] args) {
        AbstractFactory otherFactory = new OtherFactory();
        System.out.println(otherFactory.createElectronicProduct());
        System.out.println(otherFactory.createFoodProduct());

        AbstractFactory concreteFactory = new ConcreteFactory();
        System.out.println(concreteFactory.createElectronicProduct());
        System.out.println(concreteFactory.createFoodProduct());

    }
}