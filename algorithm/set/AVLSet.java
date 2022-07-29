package algorithm.set;

import algorithm.tree.AvlTree;

/**
 * @author:qiming
 * @date: 2021/11/1
 */
public class AVLSet<E extends Comparable<E>, V> implements Set<E> {
    private final AvlTree<E, Object> avlTree;

    public AVLSet() {
        avlTree = new AvlTree<>();
    }

    @Override
    public void add(E e) {
        avlTree.add(e, null);
    }

    @Override
    public void remove(E e) {
        avlTree.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return avlTree.contains(e);
    }

    @Override
    public boolean isEmpty() {
        return avlTree.isEmpty();
    }

    @Override
    public int size() {
        return avlTree.size();
    }
}
