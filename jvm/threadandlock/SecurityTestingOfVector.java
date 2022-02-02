package jvm.threadandlock;


import java.util.Vector;

/**
 * @author zqw
 * @date 2021/3/31
 */
public class SecurityTestingOfVector {

    private static final Vector<Integer> vector = new Vector<>();


    public static void main(String[] args) {
        while (true) {
            for(int i = 0;i < 10;i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(() -> {

                // Synchronization must be added to secure vector access!
                synchronized (vector) {
                    for(int i = 0;i < vector.size();i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(() -> {
                synchronized (vector) {
                    for (Integer integer : vector) {
                        System.out.println(integer);
                    }
                }
            });

            removeThread.start();
            printThread.start();

            // Do not spawn too many threads at the same time, as this will
            // cause the operating system to freeze.
            while (Thread.activeCount() > 20) {
            }
        }


    }
}
