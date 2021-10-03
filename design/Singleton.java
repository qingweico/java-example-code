package design;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

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
    private Integer age;
    private static final Object lock = new Object();

    private Student() {
    }

    // Ensure that instructions around student are not reordered
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
    // JDK9
    // Allowing local instructions of a ref to be reordered improves performance
    private static final AtomicReference<Student> ref = new AtomicReference<>();

    public static Student getRef() {
        if (ref.getAcquire() == null) {
            synchronized (lock) {
                if (ref.getAcquire() == null) {
                    ref.setRelease(student);
                }
            }
        }
        return ref.getAcquire();
    }


}
