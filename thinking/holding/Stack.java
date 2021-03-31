package thinking.holding;

import java.util.LinkedList;

/**
 * @author:周庆伟
 * @date: 2021/1/18
 */
public class Stack<T> {
    private final LinkedList<T> storage = new LinkedList<>();
    public void push(T v){storage.addFirst(v);}
    private T peek(){return storage.getFirst();}
    public T pop(){return storage.removeFirst();}
    public boolean isEmpty() {
        return storage.isEmpty();
    }
    public String toString(){
        return storage.toString();
    }
}
