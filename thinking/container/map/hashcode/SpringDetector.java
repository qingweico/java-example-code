package thinking.container.map.hashcode;

import annotation.Pass;
import util.Print;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * What will the weather be?
 *
 * @author zqw
 * @date 2021/2/24
 */
@Pass
@SuppressWarnings("all")
class SpringDetector {
    private final static int COUNT = 10;

    public static <T extends Groundhog>
    void detectSpring(Class<T> type) throws Exception {
        Constructor<T> cons = type.getConstructor(int.class);
        Map<Groundhog, Prediction> map = new HashMap<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            map.put(cons.newInstance(i), new Prediction());
        }
        Print.println("map = " + map);
        Groundhog gh = cons.newInstance(3);
        Print.println("Looking up prediction for " + gh);
        if (map.containsKey(gh)) {
            Print.println(map.get(gh));
        } else {
            Print.println("Key not found: " + gh);
        }
    }

    public static void main(String[] args) throws Exception {
        // Can't work
        // detectSpring(Groundhog.class);

        // Good! a working key
        detectSpring(Groundhog2.class);

    }
}
