package algorithm.map;

/**
 * @author zqw
 * @date 2021/10/30
 */
public interface Map<K, V> {
    void add(K k, V v);
    V remove(K k);
    boolean contains(K k);
    V get(K k);
    void set(K k, V v);
    int size();
    boolean isEmpty();

}
