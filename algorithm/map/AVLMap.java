package algorithm.map;

import algorithm.tree.AVLTree;

/**
 * @author:qiming
 * @date: 2021/11/1
 */
public class AVLMap<K extends Comparable<K>, V> implements Map<K, V> {
   private final AVLTree<K, V> avlTree;
   public AVLMap() {
      avlTree = new AVLTree<>();
   }
    @Override
    public void add(K k, V v) {
       avlTree.add(k ,v);
    }

    @Override
    public V remove(K k) {
        return avlTree.remove(k);
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }

    @Override
    public boolean contains(K k) {
        return avlTree.contains(k);
    }

    @Override
    public V get(K k) {
        return avlTree.get(k);
    }

    @Override
    public int size() {
        return avlTree.size();
    }

    @Override
    public void set(K k, V v) {
       avlTree.set(k, v);
    }
}
