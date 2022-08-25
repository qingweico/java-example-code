package algorithm.set;

/**
 * @author zqw
 * @date 2021/10/31
 */
public interface Set<E> {
    /**
     * add
     *
     * @param e E
     */
    void add(E e);

    /**
     * removeElement
     *
     * @param e E
     */
    void remove(E e);

    /**
     * contains
     *
     * @param e E
     * @return contains
     */
    boolean contains(E e);

    /**
     * isEmpty
     *
     * @return isEmpty
     */
    boolean isEmpty();

    /**
     * size
     *
     * @return size
     */
    int size();
}
