package thinking.genericity;

import util.Print;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/1/12
 */
// Storing element of type 'thinking.genericity.Orange'
// to array of 'thinking.genericity.Apple' elements will
// produce 'ArrayStoreException'
@SuppressWarnings("all")
class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruits = new Apple[10];
        // it's ok
        fruits[0] = new Apple();
        // it's ok
        fruits[1] = new Jonathan();
        try {
            // Compiler allows you to add Fruit.
            // ArrayStoreException
            fruits[0] = new Fruit();
        } catch (Exception e) {
            Print.err(e.getMessage());
        }
        try {
            // Compiler allows you to add Orange.
            // ArrayStoreException
            fruits[0] = new Orange();
        } catch (Exception e) {
            Print.err(e.getMessage());
        }
        // However, the array mechanism at runtime knows that it is dealing with an
        // Apple type and therefore throws an exception when a heterogeneous type
        // is placed on the array.

        System.out.println(Arrays.toString(fruits));
    }
}

class Fruit {
    @Override
    public String toString() {
        return "Fruit";
    }
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
