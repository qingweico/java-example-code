package design.behaviour.state;

/**
 * 状态模式 : 当一个对象的内在状态改变时, 允许改变其行为
 *
 * @author zqw
 * @date 2023/11/5
 */
public class Starter {

    private static final Object MONITOR = new Object();

    public static void main(String[] args) throws InterruptedException {


        synchronized (MONITOR) {
            Task task0 = new Task("0");
            task0.create();
            task0.start();
            task0.finish();
            Thread.sleep(1000L);
        }

        synchronized (MONITOR) {
            Task task1 = new Task("1");
            task1.create();
            task1.create();
            Thread.sleep(1000L);
        }

        synchronized (MONITOR) {
            Task task2 = new Task("2");
            task2.start();
            task2.finish();
            Thread.sleep(1000L);
        }

        synchronized (MONITOR) {
            Task task3 = new Task("3");
            task3.finish();
            task3.start();
            task3.create();
            Thread.sleep(1000L);
        }


        synchronized (MONITOR) {
            Task task4 = new Task("4");
            task4.create();
            // 隐式锁问题, 导致 print 流 和 err 流不会顺序打印
            task4.finish();
            task4.start();
            Thread.sleep(1000L);
        }
    }
}
