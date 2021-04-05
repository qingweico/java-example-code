package thinking;

/**
 * @author:qiming
 * @date: 2020/10/23
 *
 * this
 */
class Person {
    public void eat(Apple apple) {
        Apple peeled = apple.getPeeled();
        System.out.println("Yummy");
    }

    public static void main(String[] args) {
        new Person().eat(new Apple());
    }
}

class Peeler {
    static Apple peel(Apple apple) {
        return apple;
    }
}

class Apple {
    //this
    Apple getPeeled() {
        return Peeler.peel(this);
    }
}

public class Leaf {
    int i = 0;

    Leaf increment() {
        i++;
        return this;
    }

    public static void main(String[] args) {
        System.out.println(new Leaf().increment().increment().increment().i);//3
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
