package thinking.container.map.hashcode;

import java.lang.reflect.Constructor;
import java.util.*;

import static util.Print.print;

/**
 * @author:周庆伟
 * @date: 2021/2/24
 */
// What will the weather be?
public class SpringDetector {
    public static <T extends Groundhog>
    void detectSpring(Class<T> type) throws Exception {
        Constructor<T> ghog = type.getConstructor(int.class);
        Map<Groundhog, Prediction> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            map.put(ghog.newInstance(i), new Prediction());
        }
        print("map = " + map);
        Groundhog gh = ghog.newInstance(3);
        print("Looking up prediction for " + gh);
        if(map.containsKey(gh)) {
            print(map.get(gh));
        }else {
            print("Key not found: " + gh);
        }
    }

    public static void main(String[] args) throws Exception {
        // Can't work
        // detectSpring(Groundhog.class);

        // Good! a working key
        detectSpring(Groundhog2.class);

    }
}
