package effective;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * 消除过期的对象引用
 *
 * @author zqw
 * @date 2021/3/26
 */
class Article7 {
    public static void main(String[] args) {

    }
}

/**
 * Can you spot the "memory leak"?
 * Object-based collection - a prime candidate for generics
 *
 * @param <E> the generic type
 */
class Stack<E> {
    /**
     * private Object[] elements;
     */
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The elements array will contain only E instance from push(E).
     * This is sufficient to ensure type safety, but the rt type of
     * the array won't be E[]; it will always be an Object!
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        // It can be used, but it not the type safe!
        // This approach can lead to heap pollution:
        // The runtime type of array doesn't match its compile type.
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
        // Push requires elements to be of type E, so cast is correct.
        // Appropriate suppression of checked warning(@SuppressWarnings("unchecked"); (E) elements[--size])
        E result = elements[--size];
        // Eliminate obsolete reference, otherwise unintentional object retention, it's broken!
        elements[size] = null;
        return result;

    }

    /**
     * Using <? extends E> instead of <E>
     * wildcard type for a parameter that servers as an E Producer
     *
     * @param src the source collection
     */
    public void pushAll(Collection<? extends E> src) {
        for (E e : src) {
            push(e);
        }
    }

    /**
     * Wildcard type for parameter that servers as an E consumer
     *
     * @param dst the target collection
     */
    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    // PECS : producer-extends; consumer-super

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Ensure space for at least one more element, roughly
     * doubling the space each time the array needs to grow.
     */
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, size * 2 + 1);
        }
    }
}