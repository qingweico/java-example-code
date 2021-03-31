package design;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/2/3
 */
public class Singleton {

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(Student.getStudent().hashCode());
            });
        }
        Arrays.asList(threads).forEach(Thread::start);


    }

}

class Student {
    private String name;
    private int age;
    private static final Object lock = new Object();

    private Student() {
    }

    private volatile static Student student = null;


    // DCL(Double Check Lock)
    public static Student getStudent() {
        if (student == null) {
            synchronized (lock) {
                if (student == null) {
                    student = new Student();
                }
            }
        }
        return student;
    }
}
