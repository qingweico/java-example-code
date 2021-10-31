package algorithm.set;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public interface Set <E>{
   void add(E e);
   void remove(E e);
   boolean contains(E e);
   boolean isEmpty();
   int size();
}
