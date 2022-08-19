package thinking.holding;

import java.util.LinkedList;

/**
 * @author zqw
 * @date 2021/1/18
 */
class Stack<T> {
    private final LinkedList<T> storage = new LinkedList<>();
    public void push(T v){storage.addFirst(v);}
    private T peek(){return storage.getFirst();}
    public T pop(){return storage.removeFirst();}
    public boolean isEmpty() {
        return storage.isEmpty();
    }
    @Override
    public String toString(){
        return storage.toString();
    }
}
