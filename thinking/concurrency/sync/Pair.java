package thinking.concurrency.sync;

import annotation.NotThreadSafe;

/**
 * @author zqw
 * @date 2021/1/29
 */
@NotThreadSafe
public class Pair {
    private int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    class PairValueNotEqualException extends RuntimeException {
        public PairValueNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y) {
            throw new PairValueNotEqualException();
        }
    }
}
