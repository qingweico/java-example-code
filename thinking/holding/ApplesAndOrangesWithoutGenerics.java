package thinking.holding;

import java.util.ArrayList;

/**
 * @author zqw
 * @date 2021/1/17
 */
public class ApplesAndOrangesWithoutGenerics {
    @SuppressWarnings("unchecked, rawtypes")
    public static void main(String[] args) {
        ArrayList apples = new ArrayList();
        int size = 3;
        for (int i = 0; i < size; i++) {
            apples.add(new Apple());
            // Not prevented from adding an Orange to apples
            apples.add(new Orange());
        }
        for (Object apple : apples) {
            // Orange is detected only at run time
            ((Apple) apple).id();
        }
    }
}

class Apple {
    private static long counter = 0;
    private static final long ID = counter++;

    public long id() {
        return ID;
    }
}

class Orange {
}
