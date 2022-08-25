package algorithm.queue;

/**
 * @author zqw
 * @date 2021/10/29
 */
public interface Queue<E> {
    int size();

    boolean isEmpty();

    void enqueue(E e);

    E dequeue();

    E front();
}
