package algorithm.stack;

/**
 * @author zqw
 * @date 2021/10/30
 */
public interface Stack<E> {
   /**
    * Pushes an element onto the top of this stack.
    * @param e the element to be pushed onto this stack.
    */
   void push(E e);

   /**
    * Removes the object at the top of this stack and returns that
    * object as the value of this function.
    * @return
    */
   E pop();

   /**
    *
    * @return
    */
   E peek();

   /**
    *
    * @return
    */
   int size();

   /**
    *
    * @return
    */
   boolean isEmpty();
}
