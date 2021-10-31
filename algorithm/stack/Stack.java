package algorithm.stack;

/**
 * @author:qiming
 * @date: 2021/10/30
 */
public interface Stack<E> {
   void push(E e);
   E pop();
   E peek();
   int size();
   boolean isEmpty();
}
