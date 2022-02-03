package thread;

/**
 * A simple deadlock
 *
 * @author zqw
 * @date 2021/6/17
 */
class ThreadDeadlock extends Thread {
    private static final Object OBJ_A = new Object();
    private static final Object OBJ_B = new Object();
    private final boolean flag;

    public ThreadDeadlock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (OBJ_A) {
                System.out.println("objA...");
                synchronized (OBJ_B) {
                    System.out.println("objB...");
                }
            }
        } else {
            synchronized (OBJ_B) {
                System.out.println("objB...");
                synchronized (OBJ_A) {
                    System.out.println("objA...");
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadDeadlock d1 = new ThreadDeadlock(true);
        ThreadDeadlock d2 = new ThreadDeadlock(false);
        d1.start();
        d2.start();
    }
}
