package thread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2020/12/18
 */
public class ConcurrentModificationException {
    public static void main(String[] args) {

        // Using ArrayList will throw ConcurrentModificationException, because
        // ArrayList is thread-unsafe, so we could use Vector instead of ArrayList.
        // List<String> list = new Vector<>();

        // Or using the Collections.synchronizedList(Synchronous container class) also is a good choice.
        // List<String> list = Collections.synchronizedList(new ArrayList<>());

        // Or using CopyOnWriteArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("a");
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
