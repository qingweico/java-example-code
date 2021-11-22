package effective;

/**
 * 要在公有类而非公有域中使用访问方法
 *
 * @author:qiming
 * @date: 2021/11/22
 * @see java.awt.Point
 * @see java.awt.Dimension
 */
public class Article16 {
}

// Degenerate classes like this should not be public!
class rectangle {
    public int w;
    public int h;
}

// Encapsulation of data by accessor methods and mutators
class square {
    private int length;

    public square(int length) {
        this.length = length;
    }

    public square setLength(int length) {
        this.length = length;
        return this;
    }

    public int getLength() {
        return length;
    }
}

// Public class with exposed immutable fields - questionable
final class Time {
    private static final int HOUR_PRE_DAY = 24;
    private static final int MINUTES_PRE_HOUR = 60;

    public final int hour;
    public final int minute;

    public Time(int hour, int minute) {
        if (hour < 0 || hour >= HOUR_PRE_DAY) {
            throw new IllegalArgumentException("Hour: " + hour);
        }
        if (minute < 0 || minute >= MINUTES_PRE_HOUR) {
            throw new IllegalArgumentException("Min: " + minute);
        }
        this.hour = hour;
        this.minute = minute;
    }
}