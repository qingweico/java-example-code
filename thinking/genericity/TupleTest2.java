package thinking.genericity;

import util.FiveTuple;
import util.FourTuple;
import util.ThreeTuple;
import util.TwoTuple;

import static util.Tuple.tuple;

/**
 * @author:qiming
 * @date: 2021/4/10
 */
public class TupleTest2 {
    static TwoTuple<String, Integer> f() {
        return tuple("hi", 47);
    }

    static TwoTuple f2() {
        return tuple("hi", 47);
    }

    static ThreeTuple<Amphibian, String, Integer> g() {
        return tuple(new Amphibian(), "hi", 47);
    }

    static FourTuple<Vehicle, Amphibian, String, Integer> h() {
        return tuple(new Vehicle(), new Amphibian(), "hi", 47);
    }

    static FiveTuple<Vehicle, Amphibian, String, Integer, Double> k() {
        return tuple(new Vehicle(), new Amphibian(),
                "hi", 47, 11.1);
    }

    public static void main(String[] args) {
        TwoTuple<String, Integer> tt = f();
        System.out.println(tt);
        System.out.println(f2());
        System.out.println(g());
        System.out.println(h());
        System.out.println(k());
    }
}
