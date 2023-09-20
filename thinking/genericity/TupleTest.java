package thinking.genericity;

import thinking.util.FiveTuple;
import thinking.util.FourTuple;
import thinking.util.ThreeTuple;
import thinking.util.TwoTuple;
import util.Print;

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
        Print.println(tt);
        Print.println(g());
        Print.println(h());
        Print.println(k());
    }
}

class Amphibian {
}

class Vehicle {
}
