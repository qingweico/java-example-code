package design.factory;

/**
 * 工厂方法
 * @author:qiming
 * @date: 2021/12/22
 */
public abstract class Factory {
    abstract public Product factoryMethod();

    public void doSomething() {
        factoryMethod();
    }
}

class ComputerFactory extends Factory {

    @Override
    public Product factoryMethod() {
        return new Computer();
    }

    public static void main(String[] args) {

    }
}

class TelevisionFactory extends Factory {

    @Override
    public Product factoryMethod() {
        return new Television();
    }

    public static void main(String[] args) {

    }
}
