package util;

import java.util.*;

/**
 * @author zqw
 * @date 2021/4/9
 */
public final class New {
    private New() {
    }

    public static <K, V> Map<K, V> map() {
        return map(1 << 4);
    }

    public static <K, V> Map<K, V> map(int capacity) {
        return new HashMap<>(capacity);
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static <T> LinkedList<T> lList() {
        return new LinkedList<>();
    }

    public static <T> Set<T> set() {
        return new HashSet<>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<>();
    }
}
