package algorithm.set;

import algorithm.tree.BinarySearchTree;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class BinarySearchTreeSet<E extends Comparable<E>> implements Set<E> {
    private final BinarySearchTree<E> binarySearchTree;

    public BinarySearchTreeSet() {
        binarySearchTree = new BinarySearchTree<>();
    }

    @Override
    public void add(E e) {
        binarySearchTree.add(e);
    }

    @Override
    public void remove(E e) {
        binarySearchTree.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return binarySearchTree.contains(e);
    }

    @Override
    public boolean isEmpty() {
        return binarySearchTree.isEmpty();
    }

    @Override
    public int size() {
        return binarySearchTree.size();
    }
}
