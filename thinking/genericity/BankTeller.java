package thinking.genericity;

import cn.qingweico.supplier.Generator;

import java.util.*;

/**
 * Apply generics to anonymous inner classes
 *
 * @author zqw
 * @date 2021/4/10
 */

/* A very simple bank teller simulation*/

class BankTeller {
    public static void serve(Teller t, Customer c) {
        System.out.println(t + " serve " + c);
    }

    public static void main(String[] args) {
        Random random = new Random(47);
        Queue<Customer> line = new LinkedList<>();
        Generators.fill(line, Customer.generator(), 15);
        List<Teller> tellers = new ArrayList<>();
        Generators.fill(tellers, Teller.generator, 4);
        for (Customer c : line) {
            serve(tellers.get(random.nextInt(tellers.size())), c);
        }
    }
}

class Customer {
    private static int counter = 0;
    private final long id = counter++;

    private long id() {
        return id;
    }

    private Customer() {
    }

    @Override
    public String toString() {
        return "Customer: " + id();
    }

    public static Generator<Customer> generator() {
        return Customer::new;
    }
}

class Teller {
    private static long counter = 1;
    private final long id = counter++;

    private Teller() {
    }

    @Override
    public String toString() {
        return "Teller: " + id;
    }

    public static Generator<Teller> generator = Teller::new;
}
