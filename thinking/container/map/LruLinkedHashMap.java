package thinking.container.map;

import thinking.util.CountingMapData;

import java.util.LinkedHashMap;

import static util.Print.print;

/**
 *  What you can do with a LinkedHashMap?
 *
 * @author zqw
 * @date 2021/2/24
 */

class LruLinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>(
                new CountingMapData(9));
        print(linkedMap);

        // Least-recently-user order:
        // When the accessOrder parameter is true, the LinkedHashMap uses the access-based Least
        // Recently Used (LRU) algorithm, elements that have not been accessed appear at the
        // front of the queue (and can be considered to need to be deleted).
        linkedMap = new java.util.LinkedHashMap<>(16,
                0.75f,
                true);
        linkedMap.putAll(new CountingMapData(9));
        print(linkedMap);
        for (int i = 0; i < 6; i++) {
            linkedMap.get(i);
        }
        print(linkedMap);
        linkedMap.get(0);
        print(linkedMap);
    }
}
