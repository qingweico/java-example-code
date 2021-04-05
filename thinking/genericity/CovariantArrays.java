package thinking.genericity;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/1/12
 */
public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();    //it's ok
        fruits[1] = new Jonathan(); //it's ok
        try {
            // Compiler allows you to add Fruit.
            fruits[0] = new Fruit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            // Compiler allows you to add Orange.
            fruits[0] = new Orange();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // However, the array mechanism at runtime knows that it is dealing with an
        // Apple type and therefore throws an exception when a heterogeneous type
        // is placed on the array.


        System.out.println(Arrays.toString(fruits));
    }
}

class Fruit {
}

class Apple extends Fruit {
    private static int id;

    @Override
    public String toString() {
        return "Apple{" + ++id + "}";
    }
}

class Jonathan extends Apple {
    private static int id;

    @Override
    public String toString() {
        return "Jonathan{" + ++id + "}";
    }
}

class Orange extends Fruit {
    private static int id;

    @Override
    public String toString() {
        return "Orange{" + ++id + "}";
    }
}
