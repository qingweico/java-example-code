package thinking.genericity;

import util.FourTuple;

import java.util.ArrayList;

/**
 * Combining generic types to make complex generic types
 *
 * @author zqw
 * @date 2021/4/11
 */
public class TupleList<A, B, C, D> extends ArrayList<FourTuple<A, B, C, D>> {
    public static void main(String[] args) {
        TupleList<Vehicle, Amphibian, String, Integer> t = new TupleList<>();
        t.add(TupleTest.h());
        t.add(TupleTest.h());
        for(FourTuple<Vehicle, Amphibian, String, Integer> f : t) {
            System.out.println(f);
        }
    }
}
