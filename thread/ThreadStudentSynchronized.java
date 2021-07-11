package thread;

// Solve thread safety issues
class Student {
    int age;
    String name;
    boolean flag = false;
}

class GetThread implements Runnable {
    private final Student s;

    public GetThread(Student s) {
        this.s = s;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        //Student s = new Student();
        while (true) {
            synchronized (s) {
                if (!s.flag) {  // Wait if there is no data
                    try {
                        s.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(s.name + "--------------" + s.age);
                s.flag = false;
                s.notify();
            }
        }
    }
}

class SetThread implements Runnable {
    private final Student s;
    private int x = 0;

    public SetThread(Student s) {
        this.s = s;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        // Student s = new Student();
        while (true) {
            synchronized (s) {
                if (s.flag) {
                    try {
                        s.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (x % 2 == 0) {
                    s.name = "Jack";
                    s.age = 21;
                } else {
                    s.name = "Tom";
                    s.age = 22;
                }
                x++;
                s.flag = true;
                // Waking up the waiting thread does not mean that the thread has
                // the right to execute the cpu.
                s.notify();
            }
        }
    }
}

public class ThreadStudentSynchronized extends Thread {
    public static void main(String[] args) {
        // Create a student resource object
        Student s = new Student();
        // Create thread object
        SetThread set = new SetThread(s);
        GetThread get = new GetThread(s);

        Thread t1 = new Thread(set);
        Thread t2 = new Thread(get);

        t1.start();
        t2.start();
    }
}
