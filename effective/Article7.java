package effective;

import java.security.interfaces.ECKey;
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
// Object-based collection - a prime candidate for generics
class Stack<E> {
    // private Object[] elements;
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    // The elements array will contain only E instance from push(E).
    // This is sufficient to ensure type safety, but the rt type of
    // the array won't be E[]; it will always be Object!
    @SuppressWarnings("unchecked")
    public Stack() {
        // It can be used, but it not the type safe!
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        // generic array creation
        // elements = new E[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // Owning to E is a non-reifiable type, the compiler couldn't check conversion at rt.
        @SuppressWarnings("unchecked") E result = (E) elements[--size];
        // Eliminate obsolete reference, otherwise unintentional object retention, it's broken
        elements[size] = null;
        return result;

    }

    /**
     * Ensure space for at least one more element, roughly
     * doubling the space each time the array needs to grew.
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, size * 2 + 1);
        }
    }
}