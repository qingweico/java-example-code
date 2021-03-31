package thread;

//-----------------------解决线程安全问题----------------------------
//----------上锁或同步代码块-----------
//不同种类的线程操作的时候都要加琐，而且是一把琐
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
        //Student s=new Student();
        while (true) {
            synchronized (s) {
                if (!s.flag) {  //如果没有数据就等待
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
            //t1和t2抢

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
        //Student s=new Student();
        while (true) {
            synchronized (s) {
                if (s.flag) {  //这表示有数据
                    try {
                        s.wait();  //t1就要等待了
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
                s.notify();  //唤醒等待的线程，并不代表该线程就具有cpu的执行权
            }
            //t1和t2抢
        }
    }
}

public class ThreadStudentSynchronized extends Thread {
    public static void main(String[] args) {
        //创建一个学生资源对象
        Student s = new Student();
        //创建线程对象
        SetThread set = new SetThread(s);
        GetThread get = new GetThread(s);

        Thread t1 = new Thread(set);
        Thread t2 = new Thread(get);
        t1.start();
        t2.start();
    }
}
