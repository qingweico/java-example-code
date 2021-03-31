package effective;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 消除过期的对象引用
 *
 * @author:qiming
 * @date: 2021/3/26
 */
public class Article7 {
}

// Can you spot the "memory leak"?
class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }
    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }
    // Broken!
    // unintentional object retention.
    public Object pop() {
        if(size == 0) {
            throw new EmptyStackException();
        }
        return elements[--size];
    }

    public Object pop0() {
        if(size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null;
        return result;

    }

    /**
     * Ensure space for at least one more element, roughly
     * doubling the space each time the array needs to grew.
     */
    private void ensureCapacity() {
        if(elements.length == size) {
            elements = Arrays.copyOf(elements, size * 2 + 1);
        }
    }
}