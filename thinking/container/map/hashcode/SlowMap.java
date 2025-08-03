package thinking.container.map.hashcode;

import thinking.util.Countries;
import cn.qingweico.io.Print;

import java.util.*;

/**
 * A Map implements with ArrayLists
 *
 * @author zqw
 * @date 2021/2/24
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private final List<K> keys = new ArrayList<>();
    private final List<V> values = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        // The old value or null
        V oldValue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) { // Key is type object, not K
        if (!keys.contains(key)) {
            return null;
        }
        return values.get(keys.indexOf(key));
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();
        while (ki.hasNext()) {
            set.add(new MapEntry<>(ki.next(), vi.next()));
        }
        return set;
    }

    public static void main(String[] args) {
        SlowMap<String, String> m = new SlowMap<>();
        m.putAll(Countries.capitals(15));
        Print.println(m);
        Print.println(m.get("BULGARIA"));
        Print.println(m.entrySet());
    }
}
