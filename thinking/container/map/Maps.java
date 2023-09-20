package thinking.container.map;

import thinking.util.CountingMapData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static util.Print.*;

/**
 * Things you can do with Maps
 *
 * @author zqw
 * @date 2021/2/21
 */
class Maps {
    static int initialCapacity = 10;

    public static void printKeys(Map<Integer, String> map) {
        print("Size = " + map.size() + ", ");
        print("Keys: ");
        // Produces a Set of the keys
        println(map.keySet());
    }

    public static void test(Map<Integer, String> map) {
        println(map.getClass().getSimpleName());
        map.putAll(new CountingMapData(25));

        // Map has 'Set' behavior for keys
        map.putAll(new CountingMapData(25));

        printKeys(map);

        // Producing a Collection of the values:

        print("Values: ");

        println(map.values());

        println(map);

        println("map.containsKey(11): " + map.containsKey(11));

        println("map.get(11): " + map.get(11));

        println("map.containsValue(\"F0\"): " + map.containsValue("F0"));

        Integer key = map.keySet().iterator().next();
        println("First key in map: " + key);
        map.remove(key);
        printKeys(map);
        map.clear();
        println("map.isEmpty()：" + true);

        map.putAll(new CountingMapData(25));

        // Operations on the Set change the Map:
        map.keySet().removeAll(map.keySet());
        println("map.isEmpty()：" + map.isEmpty());


    }

    public static void main(String[] args) {
        test(new HashMap<>(initialCapacity));
        test(new TreeMap<>());
        test(new LinkedHashMap<>());
        test(new IdentityHashMap<>());
        test(new ConcurrentHashMap<>(initialCapacity));
        test(new WeakHashMap<>());
    }
}
