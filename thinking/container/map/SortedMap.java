package thinking.container.map;

import thinking.util.CountingMapData;

import java.util.Iterator;
import java.util.TreeMap;

import static util.Print.print;

/**
 *  What you can do with a TreeMap?
 *
 * SortedMap
 * @author zqw
 * @date 2021/2/23
 */
class SortedMap {
    public static void main(String[] args) {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(new CountingMapData(10));
        print(sortedMap);

        Integer low = sortedMap.firstKey();
        Integer high = sortedMap.lastKey();
        print(low);
        print(high);

        Iterator<Integer> iterator = sortedMap.keySet().iterator();
        for (int i = 0; i <= 6; i++) {
            if (i == 3) {
                low = iterator.next();
            }
            if (i == 6) {
                high = iterator.next();
            } else {
                iterator.next();
            }
        }

        print(low);
        print(high);

        // [low, high)
        print(sortedMap.subMap(low, high));
        // [0, high)
        print(sortedMap.headMap(high));
        // [low, map.size)
        print(sortedMap.tailMap(low));
    }
}







