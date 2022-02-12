package algorithm.map;

import algorithm.tree.RedBlackTree;

/**
 * @author zqw
 * @date 2021/11/2
 */
public class RedBlackTreeMap <K extends Comparable<K>, V> implements Map<K, V>{
   private final RedBlackTree<K, V> redBlackTree;
   public RedBlackTreeMap() {
      redBlackTree = new RedBlackTree<>();
   }
   @Override
   public void add(K k, V v) {
      redBlackTree.add(k, v);
   }

   @Override
   public V remove(K k) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isEmpty() {
      return redBlackTree.isEmpty();
   }

   @Override
   public boolean contains(K k) {
      return redBlackTree.contains(k);
   }

   @Override
   public V get(K k) {
      return redBlackTree.get(k);
   }

   @Override
   public int size() {
      return redBlackTree.size();
   }

   @Override
   public void set(K k, V v) {
      redBlackTree.set(k, v);
   }
}
