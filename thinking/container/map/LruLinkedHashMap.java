package thinking.container.map;

import thinking.util.CountingMapData;
import cn.qingweico.io.Print;

import java.util.LinkedHashMap;

/**
 *  What can you do with a LinkedHashMap?
 *
 * @author zqw
 * @date 2021/2/24
 */

class LruLinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>(
                new CountingMapData(9));
        Print.println(linkedMap);

        // Least-recently-user order:
        // When the accessOrder parameter is true, the LinkedHashMap uses the access-based Least
        // Recently Used (LRU) algorithm, elements that have not been accessed appear at the
        // front of the queue (and can be considered to need to be deleted).
        linkedMap = new java.util.LinkedHashMap<>(16,
                0.75f,
                true);
        linkedMap.putAll(new CountingMapData(9));
        Print.println(linkedMap);
        for (int i = 0; i < 6; i++) {
            linkedMap.get(i);
        }
        Print.println(linkedMap);
        linkedMap.get(0);
        Print.println(linkedMap);
    }
}
