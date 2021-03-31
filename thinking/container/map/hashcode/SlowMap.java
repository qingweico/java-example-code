package thinking.container.map.hashcode;

import util.Countries;

import java.util.*;

import static util.Print.print;

/**
 * A Map implements with ArrayLists
 *
 * @author:周庆伟
 * @date: 2021/2/24
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<>();
    private List<V> values = new ArrayList<>();
    public V put(K key, V value) {
        // The old value or null
        V oldValue = get(key);
        if(!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        }
        else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }
    public V get(Objects key) { // Key is type object, not K
        if(!keys.contains(key)) {
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
            set.add(new MapEntry<K, V>(ki.next(), vi.next()));
        }
        return set;
    }

    public static void main(String[] args) {
        SlowMap<String, String> m = new SlowMap<>();
        m.putAll(Countries.capitals(15));
        print(m);
        print(m.get("BULGARIA"));
        print(m.entrySet());


    }
}
