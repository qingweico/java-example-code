package thinking.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:qiming
 * @date: 2021/1/12
 */
public class GenericsAndCovariance {
    public static void main(String[] args) {
        // Wildcard allow covariance
        List<? extends Fruit> list = new ArrayList<Apple>();

        // Compiler Error: can't add any type of object
        // list.add(new Apple());
        // list.add(new Fruit());
        // list.add(new Object());

        // Legal but uninteresting
        list.add(null);
        // We know that it returns Fruit at least.
        Fruit f = list.get(0);

        // List<? extends Fruit>
        // What this expression means is 'With a list of any types inherited from Fruit,
        // this doesn't really mean that the list will hold any types of Fruit'.

    }
}
