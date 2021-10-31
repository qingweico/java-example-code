package algorithm.map;

/**
 * @author:qiming
 * @date: 2021/10/30
 */
public interface Map<K, V> {
    void add(K k, V v);
    V remove(K k);
    boolean isEmpty();
    boolean contains(K k);
    V get(K k);
    int size();
    void set(K k, V v);
}
