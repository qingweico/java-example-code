package thinking.holding;

import java.util.ArrayList;

/**
 * @author:周庆伟
 * @date: 2021/1/17
 */
public class ApplesAndOrangesWithoutGenerics {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ArrayList apples = new ArrayList();
        for (int i = 0; i < 3; i++) {
            apples.add(new Apple());
            // Not prevented from adding an Orange to apples
            apples.add(new Orange());
        }
        for (int i = 0; i < apples.size(); i++) {
            // Orange is detected only at run time
            ((Apple) apples.get(i)).id();
        }
    }
}

class Apple {
    private static long counter;
    private static final long id = counter++;

    public long id() {
        return id;
    }
}

class Orange {
}
