package util.collection;

import java.util.*;

/**
 * @author zqw
 * @date 2021/4/9
 */
public final class CollUtils {
    private CollUtils() {
    }

    public static <K, V> Map<K, V> map() {
        return map(mapSize(1 << 4));
    }

    public static <K, V> Map<K, V> map(int capacity) {
        return new HashMap<>(capacity);
    }

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<>();
    }

    public static <T> Set<T> set() {
        return new HashSet<>();
    }

    public static <T> Queue<T> queue() {
        return new LinkedList<>();
    }

    public static int mapSize(int exceptedSize) {
        return (int) ((float) exceptedSize / 0.75F + 1.0F);
    }
}
