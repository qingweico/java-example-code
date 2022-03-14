package algorithm.set;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public interface Set <E>{
   /**
    *
    * @param e E
    */
   void add(E e);
   /**
    *
    * @param e E
    */
   void remove(E e);
   /**
    *
    * @param e E
    */
   boolean contains(E e);
   /**
    *
    */
   boolean isEmpty();
   /**
    *
    */
   int size();
}
