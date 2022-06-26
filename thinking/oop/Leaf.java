package thinking;

/**
 * this
 *
 * @author zqw
 * @date 2020/10/23
 */

public class Leaf {
    int i = 0;

    Leaf increment() {
        i++;
        return this;
    }

    public static void main(String[] args) {
        System.out.println(new Leaf().increment().increment().increment().i);
    }
}

class Person {
    public void eat(Apple apple) {
        Apple peeled = apple.getPeeled();
        System.out.println("Yummy! " + peeled);
    }

    public static void main(String[] args) {
        Person p = new Person();
        Apple apple = new Apple();
        p.eat(apple);
        Person other = new Person();
        other.eat(apple);
    }
}

class Peeler {
    static Apple peel(Apple apple) {
        return apple;
    }
}

class Apple {
    Apple getPeeled() {
        return Peeler.peel(this);
    }
}
