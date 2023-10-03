package collection.map;

import util.Print;
import util.RandomDataGenerator;
import util.collection.CollUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用 Stream 对 Map 按照 K 或者 V 进行排序
 *
 * @author zqw
 * @date 2023/10/1
 */
class MapSort {
    public static void main(String[] args) {
        int size = 10;
        Map<String, Integer> map = new HashMap<>(CollUtils.mapSize(size));
        for (int i = 0; i < size; i++) {
            map.put(RandomDataGenerator.randomAddress(false), RandomDataGenerator.randomInt());
        }
        Print.toPrint(map);
        Print.println("\n\n//////// SortedMapByKey //////////");
        LinkedHashMap<String, Integer> sortedMapByKey = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (newV, olderV) -> olderV, LinkedHashMap::new));

        Print.toPrint(sortedMapByKey);


        Print.println("\n\n//////// SortedMapByValue //////////");

        LinkedHashMap<String, Integer> sortedMapByValue = map.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (newV, olderV) -> olderV, LinkedHashMap::new));

        Print.toPrint(sortedMapByValue);
        Print.println("\n\n//////// TreeMap //////////");
        // 或者直接使用 TreeMap(自定义比较器只能排序Key) 按照 Key 字典顺序倒序
        TreeMap<String, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.putAll(map);
        Print.toPrint(treeMap);

    }
}
