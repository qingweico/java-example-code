package thinking.container.map.hashcode;
import java.util.*;
/**
 * @author zqw
 * @date 2021/2/24
 */

// A simple Map.Entry for sample Map implementations.

public class MapEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V v) {
        V result = value;
        value = v;
        return result;
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^
                (value == null ? 0 : value.hashCode());
    }
    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if (!(o instanceof MapEntry)) {
            return false;
        }
        MapEntry me = (MapEntry) o;
        return (key == null ?
                        me.getKey() == null : key.equals(me.getKey())) &&
                        (value == null ?
                                me.getValue() == null : value.equals(me.getValue()));
    }

    @Override
    public String toString() {
        return key + " = " + value;
    }
}
