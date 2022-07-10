package thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock
 *
 * @author zqw
 * @date 2022/2/4
 */
class Point {
    private double x, y;
    final StampedLock sl = new StampedLock();

    double distanceFromOrigin() {
        // 乐观读
        long stamp = sl.tryOptimisticRead();
        double curX = x, curY = y;
        if (!sl.validate(stamp)) {
            // 存在写操作; 将乐观读升级为悲观读锁
            stamp = sl.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Double.parseDouble(String.format("%.2f", Math.sqrt(curX * curY + curY * curY)));
    }

    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.readLock();
        try {
            while (x == 0 && y == 0) {
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }

    void located(double inputX, double inputY) {
        long stamp = sl.writeLock();
        try {
            x = inputX;
            y = inputY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }
}
