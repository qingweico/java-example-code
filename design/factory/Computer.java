package design.factory;

/**
 * @author:qiming
 * @date: 2021/12/22
 */
class Computer extends ElectronicProduct implements Product {
    @Override
    public String toString() {
        return "Computer";
    }
}
