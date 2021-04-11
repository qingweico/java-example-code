package thinking.genericity;

import util.Generator;

import java.util.*;

/**
 * Apply generics to anonymous inner classes
 *
 * @author:qiming
 * @date: 2021/4/10
 */

// A very simple bank teller simulation
public class BankTeller {
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

    public String toString() {
        return "Customer: " + id;
    }

    public static Generator<Customer> generator() {
        return new Generator<Customer>() {
            @Override
            public Customer next() {
                return new Customer();
            }
        };
    }
}

class Teller {
    private static long counter = 1;
    private final long id = counter++;

    private Teller() {
    }

    public String toString() {
        return "Teller: " + id;
    }

    public static Generator<Teller> generator = Teller::new;
}