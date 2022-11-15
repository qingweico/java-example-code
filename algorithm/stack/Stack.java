package algorithm.stack;

/**
 * @author zqw
 * @date 2021/10/30
 */
public interface Stack<E> {
    /**
     * Pushes an element onto the top of this stack.
     *
     * @param e the element to be pushed onto this stack.
     */
    void push(E e);

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return E
     */
    E pop();

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack
     *
     * @return E
     */
    E peek();

    /**
     * stack size
     *
     * @return stack size
     */
    int size();

    /**
     * this stack is empty or not.
     *
     * @return Tests if this stack is empty.
     */
    boolean isEmpty();
}
