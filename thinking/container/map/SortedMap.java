package thinking.container.map;

import thinking.util.CountingMapData;
import util.Print;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *  What you can do with a TreeMap?
 * <p>
 * SortedMap
 * @author zqw
 * @date 2021/2/23
 */
class SortedMap {
    public static void main(String[] args) {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(new CountingMapData(10));
        Print.println(sortedMap);

        Integer low = sortedMap.firstKey();
        Integer high = sortedMap.lastKey();
        Print.println(low);
        Print.println(high);

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

        Print.println(low);
        Print.println(high);

        // [low, high)
        Print.println(sortedMap.subMap(low, high));
        // [0, high)
        Print.println(sortedMap.headMap(high));
        // [low, map.size)
        Print.println(sortedMap.tailMap(low));
    }
}







