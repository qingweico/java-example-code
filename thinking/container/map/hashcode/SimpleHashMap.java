package thinking.container.map.hashcode;

import thinking.util.Countries;
import util.Print;

import java.util.*;

/**
 * A demonstration hashed Map
 *
 * @author zqw
 * @date 2021/4/3
 */
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {

    /** Choose a prime number for the hash table size ,to achieve a uniform distribute:*/
    static final int SIZE = 997;

    /**You can't have a physical array of generics, but you can upcast to one:*/
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    @Override
    public V put(K key, V value) {
        V olderValue = null;
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                olderValue = iPair.getValue();
                // Replace old with new
                it.set(pair);
                found = true;
                break;
            }
        }
        if (!found) {
            buckets[index].add(pair);
        }
        return olderValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null) {
            return null;
        }
        for (MapEntry<K, V> iPair : buckets[index]) {
            if (iPair.getKey().equals(key)) {
                return iPair.getValue();
            }
        }
        return null;

    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            set.addAll(bucket);
        }
        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> m = new SimpleHashMap<>();
        m.putAll(Countries.capitals(25));
        Print.println(m);
        Print.println(m.get("ERITREA"));
        Print.println(m.entrySet());
    }
}
