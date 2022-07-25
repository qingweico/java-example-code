package effective;

import lombok.Data;

/**
 * 要在公有类而非公有域中使用访问方法
 *
 * @author zqw
 * @date 2021/11/22
 * @see java.awt.Point
 * @see java.awt.Dimension
 */
public class Article16 {
    public static void main(String[] args) {

    }
}

// Degenerate classes like this should not be public!
@Data
class Rectangle {
    public int w;
    public int h;
}

// Encapsulation of data by accessor methods and mutators
@Data
class Square {
    private int length;

    public Square(int length) {
        this.length = length;
    }

    public Square setLength(int length) {
        this.length = length;
        return this;
    }

    public int getLength() {
        return length;
    }
}

// Public class with exposed immutable fields - questionable
@Data
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

    public static void main(String[] args) {
        Time time = new Time(12, 23);
        // ?
        // time.hour = 20;
        System.out.println(time);
    }
}