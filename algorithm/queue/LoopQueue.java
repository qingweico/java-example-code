package algorithm.queue;

/**
 * @author zqw
 * @date 2021/10/30
 */
public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front, tail;
    private int size;

    @SuppressWarnings("unchecked")
    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        this.front = this.tail = this.size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(E e) {
        if ((tail + 1) % capacity() == front) {
            resize(capacity() << 1);
        }
        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;

    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("queue null!");
        }
        E old = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if (size == capacity() >> 2 && capacity() >> 1 != 0) {
            resize(capacity() >> 1);
        }
        return old;
    }

    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked") E[] newArray = (E[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            newArray[i] = data[(i + front) % data.length];
        }
        data = newArray;
        front = 0;
        tail = size;
    }

    @Override
    public E front() {
        if (isEmpty()) {
            throw new IllegalStateException("queue null!");
        }
        return data[front];
    }

    public int capacity() {
        return data.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Loop Queue: size = %d, capacity = %d\n", size, capacity()));
        res.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            res.append(data[i]);
            if ((i + 1) % data.length != tail) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }
}
