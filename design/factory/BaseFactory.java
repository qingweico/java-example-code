package design.factory;

/**
 * 工厂方法
 *
 * @author zqw
 * @date 2021/12/22
 */
abstract class BaseFactory {
    /**
     * Factory Method of Product
     * @return Product
     */
    abstract public Product factoryMethod();

    public void doSomething() {
        @SuppressWarnings("unused")
        Product product = factoryMethod();
    }
}

class ComputerFactory extends BaseFactory {

    @Override
    public Product factoryMethod() {
        return new Computer();
    }

    public static void main(String[] args) {

    }
}

class TelevisionFactory extends BaseFactory {

    @Override
    public Product factoryMethod() {
        return new Television();
    }

    public static void main(String[] args) {

    }
}
