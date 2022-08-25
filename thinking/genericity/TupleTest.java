package thinking.genericity;

import util.FiveTuple;
import util.FourTuple;
import util.ThreeTuple;
import util.TwoTuple;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/4/9
 */
public class TupleTest {
    static TwoTuple<String, Integer> f() {
        // Autoboxing converts the int to Integer:
        return new TwoTuple<>("hi", 47);
    }

    static ThreeTuple<Amphibian, String, Integer> g() {
        return new ThreeTuple<>(new Amphibian(), "hi", 47);
    }

    static FourTuple<Vehicle, Amphibian, String, Integer> h() {
        return new FourTuple<>(new Vehicle(), new Amphibian(), "hi", 47);
    }

    static FiveTuple<Vehicle, Amphibian, String, Integer, Double> k() {
        return new FiveTuple<>(new Vehicle(), new Amphibian(), "hi", 47, 11.1);
    }

    public static void main(String[] args) {
        TwoTuple<String, Integer> tt = f();
        // Compile error: final
        // tt.first = "there";
        print(tt);
        print(g());
        print(h());
        print(k());
    }
}

class Amphibian {
}

class Vehicle {
}