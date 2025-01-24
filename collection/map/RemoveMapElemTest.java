package collection.map;


import org.testng.annotations.Test;
import util.Print;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 移除 HashMap 中元素的几种方法
 * @author zqw
 * @date 2023/3/18
 */
public class RemoveMapElemTest {
    private static final Map<Integer, String> INIT_MAP = Stream.iterate(1, e -> e + 1)
            .limit(4)
            .collect(Collectors.toMap(i -> i, i -> UUID.randomUUID().toString()));

    @Test
    public void removeFor() {

        //// 使用 For 循环删除
        Set<Map.Entry<Integer, String>> set = new CopyOnWriteArraySet<>(INIT_MAP.entrySet());

        int tarKey = 1;
        for(Map.Entry<Integer, String> entities : set) {
            if(entities.getKey().equals(tarKey)) {
                INIT_MAP.remove(entities.getKey());
            }
        }
        Print.printMap(INIT_MAP);
    }

    @Test
    public void removeForEach() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(INIT_MAP);
        map.forEach((k, v) -> {
           if(k.equals(1)) {
               map.remove(k);
            }
        });
        Print.printMap(map);
    }

    @Test
    public void iterRemove() {
        // 底层使用迭代器
        INIT_MAP.entrySet().removeIf(entry -> entry.getKey().equals(1));
        Print.printMap(INIT_MAP);
    }

    @Test
    public void streamRemove() {
        Map<Integer,String> map = INIT_MAP.entrySet().stream().filter(entry -> entry.getKey() != 1).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        Print.printMap(map);
    }
}
