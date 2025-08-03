package effective;

import annotation.Pass;
import cn.qingweico.io.Print;

import java.awt.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 覆盖equals时请遵守通用约定
 * Object的规范
 * x != null
 * 1>:  自反性 x.equals(x) return true
 * 2>:  对称性 y.equals(x) return true; x.equals(y) return true
 * 3>:  传递性 x.equals(y) return true, y.equals(z), return true; x.equals(z) return true
 * 4>:  一致性 Calling x.equals(y) multiple times always returns consistent results as
 * long as the information used in the object has not been modified.
 * 5>:  非空性 x.equals(null) return false;
 *
 * @author zqw
 * @date 2020/11/1
 */
@Pass
@SuppressWarnings("all")
final class CaseInsensitiveString {
    private final String s;

    /**
     * Solved! Remove code that attempts to interoperate with String.
     * return o instanceof CaseInsensitiveString &&
     * s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
     * Broken - violates symmetry
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if (o instanceof String) {
            return s.equalsIgnoreCase((String) o);
        }
        return false;

    }
    // Remainder omitted

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);

    }

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        // true
        System.out.println(cis.equals(s));
        // false
        System.out.println(s.equals(cis));
    }
}

/**
 * Using getClass() instead of instanceof in the equals' method extends the instantiable class
 * and adds new components while preserving the equals component.
 */
class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Violates Liskov substitution principle
     * if (this == o) return true;
     * if (o == null || getClass() != o.getClass()) return false;
     * Point point = (Point) o;
     * return x == point.x &&
     * y == point.y;
     */
    @Override
    public boolean equals(Object o) {
        // Broken
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class ColorPoint extends Point {
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    /**
     * Good
     * if (this == o) return true;
     * if (o == null || getClass() != o.getClass()) return false;
     * if (!super.equals(o)) return false;
     * ColorPoint cp = (ColorPoint) o;
     * return Objects.equals(color, cp.color);
     * <p>
     * Broken - violates symmetry
     * if (!(o instanceof ColorPoint)) return false;
     * return super.equals(o) && ((ColorPoint) o).color == color;
     * <p>
     * Broken - violates transitivity!
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        // If o is a normal Point, do a color-blind comparison.
        if (!(o instanceof ColorPoint)) {
            return o.equals(this);
        }
        // The o is a ColorPoint, do a full comparison.
        return super.equals(o) && ((ColorPoint) o).color == color;
    }

    public static void main(String[] args) {
        ColorPoint cp = new ColorPoint(1, 2, Color.GREEN);
        Point p = new Point(1, 2);
        Print.println(p.equals(cp));
        Print.println(cp.equals(p));
        ColorPoint p2 = new ColorPoint(1, 2, Color.RED);
        // true
        Print.println(cp.equals(p));
        // true
        Print.println(p.equals(p2));
        // false
        Print.println(cp.equals(p2));

    }
}
@Pass
@SuppressWarnings("all")
class SmellPoint extends Point {
    public enum Smell {
        /**
         * ~
         */
        TASTY, SALTY
    }

    private final Smell smell;

    public SmellPoint(int x, int y, Smell smell) {
        super(x, y);
        this.smell = smell;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        // If o is a normal Point, do a smell-blind comparison.
        if (!(o instanceof SmellPoint)) {
            return o.equals(this);
        }
        // The o is a SmellPoint, do a full comparison.
        return super.equals(o) && ((SmellPoint) o).smell == smell;
    }

    public static void main(String[] args) {
        ColorPoint myColorPoint = new ColorPoint(1, 2, Color.RED);
        SmellPoint mySmellPoint = new SmellPoint(1, 2, Smell.SALTY);
        // StackOverflowError
        System.out.println(myColorPoint.equals(mySmellPoint));
    }

}

class CounterPoint extends Point {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    public CounterPoint(int x, int y) {
        super(x, y);
        COUNTER.incrementAndGet();
    }

    public static int numberCreated() {
        return COUNTER.get();
    }
}

class Article10 {

    /**
     * Initialize unitCircle to contain all Points on the unit circle.
     */
    private static final Set<Point> UNIT_CIRCLE = Set.of(
            new Point(1, 0), new Point(0, 1),
            new Point(-1, 0), new Point(0, -1)
    );

    public static boolean onUnitCircle(Point p) {
        return UNIT_CIRCLE.contains(p);
    }

    public static void main(String[] args) {
        System.out.println(onUnitCircle(new CounterPoint(1, 0)));
        System.out.println(CounterPoint.numberCreated());
    }

}
