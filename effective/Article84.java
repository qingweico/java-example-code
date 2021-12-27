package effective;

/**
 * 不要依赖于线程调度器
 * @author:qiming
 * @date: 2021/11/12
 */
public class Article84 {

}
// Awful CountDownLatch implementation - busy-waits incessantly
class SlowCountDownLatch {
    private int count;
    public SlowCountDownLatch(int count) {
        if(count < 0) {
            throw new IllegalArgumentException(count + "< 0");
        }
        this.count = count;
    }
    public void await() {
        while (true) {
            synchronized (this) {
                if(count == 0) {
                    return;
                }
            }
        }
    }
    public synchronized void countDown() {
        if(count != 0) {
            count--;
        }
    }

    public static void main(String[] args) {

    }
}
