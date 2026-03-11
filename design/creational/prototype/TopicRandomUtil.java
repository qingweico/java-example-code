package design.creational.prototype;

import java.util.*;

/**
 * @author zqw
 * @date 2026/1/15
 */
class TopicRandomUtil {
    static Topic random(Map<String, String> option, String key) {
        List<String> keys = new ArrayList<>(option.keySet());
        List<String> shuffledKeys = new ArrayList<>(keys);
        Collections.shuffle(shuffledKeys);

        Map<String, String> optionNew = new HashMap<>();
        String keyNew = null;

        // A -> 1    C -> 1
        // B -> 2    A -> 2
        // C -> 3    D -> 3
        // D -> 4    B -> 4
        for (int i = 0; i < keys.size(); i++) {
            String oldKey = keys.get(i);
            String newKey = shuffledKeys.get(i);

            optionNew.put(newKey, option.get(oldKey));
            // 原正确答案如果是A, 现在C对应的答案就是正确答案, 所以当遍历到
            // A 时, 此时需要将替代 A 的那个 key(就是C) 替换成正确答案
            if (oldKey.equals(key)) {
                keyNew = newKey;
            }
        }
        return new Topic(optionNew, keyNew);
    }
}
