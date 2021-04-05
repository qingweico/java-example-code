package thread;

class Person {
    private int age;
    private String name;
    private boolean flag;

    public synchronized void set(String name, int age) {
        if (this.flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        this.age = age;
        this.flag = true;
        this.notify();
    }

    public synchronized void get() {
        if (!this.flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.name + " -------------- " + this.age);
        this.flag = false;
        this.notify();
    }
}

class Get implements Runnable {
    private final Person p;

    public Get(Person p) {
        this.p = p;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            p.get();
        }
    }
}

class Set implements Runnable {
    private final Person p;
    private int x = 0;

    public Set(Person p) {
        this.p = p;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            if (x % 2 == 0) {
                p.set("Jack", 21);
            } else {
                p.set("Tom", 22);
            }
            x++;
        }
    }
}

public class ThreadStudentSynchronizedMethod {
    public static void main(String[] args) {
        // Create a resource object
        Person s = new Person();

        // Create thread object
        Set set = new Set(s);
        Get get = new Get(s);

        Thread t1 = new Thread(set);
        Thread t2 = new Thread(get);

        t1.start();
        t2.start();
    }
}
